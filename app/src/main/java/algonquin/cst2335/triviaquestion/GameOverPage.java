/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: The class GameOverPage is used to retrieve the user's username. The input will persist on the page regardless
 * if the class changes or the app exited. The points and the username will be displayed and their
 * value will be passed onto the next activity.
 * */
package algonquin.cst2335.triviaquestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.triviaquestion.databinding.InputPageBinding;

/**
 * The class GameOverPage is used to retrieve the user's username. The input will persist on the page regardless
 * if the class changes or the app exited. The points and the username will be displayed and their
 * value will be passed onto the next activity.
 */
public class GameOverPage extends AppCompatActivity {
    /**
     * model is used to prepare and managed data for Questionnaire_Page and handles communication between the class. It is the business logic
     * variableBinding is the binding class that represents the layout xml used and allow access for said xml variable
     * counter is the counter value from the Activity Questionnaire_Page. It represents the total points.
     * player is a string that represents username from the user's input.
     */
    private ProjectViewModel model;
    private InputPageBinding variableBinding;

    private int counter;

    private String player;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_1){
            AlertDialog.Builder builder = new AlertDialog.Builder(GameOverPage.this);
            builder.setMessage(getString(R.string.message))
                    .setTitle(getString(R.string.title2))
                    .setPositiveButton(getString((R.string.gotit)), ((dialog, clk) -> {
                        dialog.cancel();
                    }));

            builder.create().show();
        }else
            Toast.makeText(GameOverPage.this, getString(R.string.denyAction), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fromPrevious = getIntent();
        int point = fromPrevious.getIntExtra("counter", counter);

        variableBinding = InputPageBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        setSupportActionBar(variableBinding.myToolbar);


        model = new ViewModelProvider(this).get(ProjectViewModel.class);
        variableBinding.displayPoints.setText(String.valueOf(point));

        /*Creating a SharedPreference object where user input will be pre-filled.
        * The username is the naame of the file that will be opened for saving.
        * Context.MODE_PRIVATE = app that created the file can open it.
        *  */
        SharedPreferences prefs = getSharedPreferences("username", Context.MODE_PRIVATE);

        /* Parameter default value is null in case there is nothing in the file associated with the key */
        player = prefs.getString("UserName", "");
        EditText editText = findViewById(R.id.editText);
        editText.setText(player);


        variableBinding.inputSubmitBtn.setOnClickListener(clk ->{
            String username = editText.getText().toString();

            Intent nextPage = new Intent(GameOverPage.this, LeadershipBoardPage.class);
            nextPage.putExtra("username", editText.getText().toString());
            nextPage.putExtra("point", point);
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
