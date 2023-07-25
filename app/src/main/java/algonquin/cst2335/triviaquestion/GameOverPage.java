package algonquin.cst2335.triviaquestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.triviaquestion.databinding.InputPageBinding;
import algonquin.cst2335.triviaquestion.databinding.LeadershipDetailsLayoutBinding;

public class GameOverPage extends AppCompatActivity {
    private QuestionPageViewModel model;
    private InputPageBinding variableBinding;

    private LeadershipDetailsLayoutBinding binding;
    private int counter;

    private RecyclerView.Adapter myAdapter;

    ArrayList<PlayerInformation> topPlayers;
    ArrayList<String> top_Player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);

        Intent fromPrevious = getIntent();
        int point = fromPrevious.getIntExtra("counter", counter);

        variableBinding = InputPageBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

//        binding = LeadershipDetailsLayoutBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.leadership_details_layout);

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
            /*-----------Start Saving the Information----------------*/
            Intent nextPage = new Intent(GameOverPage.this, LeadershipBoardPage.class);
            nextPage.putExtra("username", editText.getText().toString());
            /*Used to say the player's username that was typed in the EditText.*/
            SharedPreferences.Editor editor = prefs.edit();
            /*Saving the string to the file username that was opened using the command*/
            editor.putString("UserName", editText.getText().toString());
            /*apply() is used to write the data in the background so the GUI doesn't slow down.*/
            editor.apply();
            startActivity(nextPage);
            /*-----------End Saving the Information----------------*/


            /*-----------Start Adding data to the arrayList now----*/

            /*This should be adding the username when the button is click*/
//            top_Player.add(variableBinding.editText.getText().toString());
//
//            /*This should be adding the user's total points from the game*/
//            top_Player.add(String.valueOf(counter));
//
//            myAdapter.notifyItemInserted(topPlayers.size()-1);
            /*-----------End Adding data to the arrayList now------*/

        });

    }

    /*Class represents an object for representing everything that goes on a row in a list*/
//    class MyRowHolder extends RecyclerView.ViewHolder{
//        TextView place;
//        TextView score;
//        TextView highscore;
//        public MyRowHolder(@NonNull View itemView){
//            super(itemView);
//            place = itemView.findViewById(R.id.place);
//            score = itemView.findViewById(R.id.score);
//            highscore = itemView.findViewById(R.id.highscore);
//        }
//    }
}
