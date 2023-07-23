package algonquin.cst2335.triviaquestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.triviaquestion.databinding.InputPageBinding;

public class GameOverPage extends AppCompatActivity {
    private QuestionPageViewModel model;
    private InputPageBinding variableBinding;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        Intent fromPrevious = getIntent();
        int point = fromPrevious.getIntExtra("counter", counter);

        variableBinding = InputPageBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model = new ViewModelProvider(this).get(QuestionPageViewModel.class);
        variableBinding.displayPoints.setText(String.valueOf(point));
        /*Creating a SharedPreference object where user input will be pre-filled.
        * The username is the naame of the file that will be opened for saving.
        * Context.MODE_PRIVATE = app that created the file can open it.
        *  */
        SharedPreferences prefs = getSharedPreferences("username", Context.MODE_PRIVATE);

        /* Parameter default value is null in case there is nothing in the file associated with the key */
        String player = prefs.getString("UserName", "");
        EditText editText = findViewById(R.id.editText);
        editText.setText(player);

        variableBinding.inputSubmitBtn.setOnClickListener(clk ->{
            Intent nextPage = new Intent(GameOverPage.this, LeadershipBoardPage.class);
            nextPage.putExtra("username", editText.getText().toString());

            /*Used to say the player's username that was typed in the EditText.*/
            SharedPreferences.Editor editor = prefs.edit();

            /*Saving the string to the file username that was opened using the command*/
            editor.putString("UserName", editText.getText().toString());

            /*apply() is used to write the data in the background so the GUI doesn't slow down.*/
            editor.apply();
            startActivity(nextPage);
        });

    }
}
