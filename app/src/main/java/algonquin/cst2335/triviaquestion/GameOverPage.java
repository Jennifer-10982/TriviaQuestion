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

public class GameOverPage extends AppCompatActivity {
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
            builder.setMessage("1. Pick a Category\n" +
                            "2. Select the Right Answer and Click Submit.\n" +
                            "\t Correct: +20 points. Yay!\n" +
                            "\t Incorrect: -30 points. Booo!\n\n" +
                            "3. Keep Playing Until You Can Get a Total of 5 Correct Answer!\n\n" +
                            "GOOD LUCK!")
                    .setTitle("How to Play: ")
                    .setPositiveButton("Got It!", ((dialog, clk) -> {
                        dialog.cancel();
                    }));

            builder.create().show();
        }else
            Toast.makeText(GameOverPage.this, "Cannot Perform Deletion", Toast.LENGTH_SHORT).show();
        return true;
    }


    //    PlayerInformationDAO pDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Use for opening the database*/
//        InformationDatabase db = Room.databaseBuilder(getApplicationContext(),
//                InformationDatabase.class,"database-name").build();
//        pDAO= db.cmDAO();

//        setContentView(R.layout.input_page);

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

//            PlayerInformation player_info = new PlayerInformation(username, String.valueOf(point));
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(()->{
//                pDAO.insertInformation(player_info);
//            });
            startActivity(nextPage);
        });
    }
}
