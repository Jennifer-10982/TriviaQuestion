/*
* Student Name: Jennifer Huynh
* Student Number: 041086110
* Lab Section: CST2335_022
* Due Date: 07/08/23
* Description: The LeadershipBoardPage class is a subclass that will retrieve the dataset from the database and to display the top 10 player, their
 * ranks ranging from 1 to 10, and their highscores and place the value into RecycleView.
* */
package algonquin.cst2335.triviaquestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.triviaquestion.databinding.LeadershipDetailsLayoutBinding;
import algonquin.cst2335.triviaquestion.databinding.ScoreListBinding;

/**
 * The LeadershipBoardPage class is a subclass that will retrieve the dataset from the database and to display the top 10 player, their
 * ranks ranging from 1 to 10, and their highscores and place the value into RecycleView.
 */
public class LeadershipBoardPage extends AppCompatActivity {
    /**
     * model is used to prepare and managed data for Questionnaire_Page and handles communication between the class. It is the business logic
     * variableBinding is the binding class that represents the layout xml used and allow access for said xml variable
     * ranking is an ArrayList used to contain all the information regarding the player from the database.
     * pDAO represents the data access object use. pDAO is use to interact with the database to retrieve and/or manipulate the data in the database.
     * myAdapter is used retrieve and associate the data into the recycleView.
     * point is the total points the player have achieved throughout the game. The points contains only whole numbers.
     * username is a string variable that contains the name that the player have inserted after they have completed the game. It can only contain alphanumerics
     * position is the position (the index) from the array list
     */
    private LeadershipDetailsLayoutBinding variableBinding;
    PlayerInformationDAO pDAO;
    ArrayList<PlayerInformation> ranking;
    private RecyclerView.Adapter myAdapter;
    private int point;
    private String username;
    int position;

    ProjectViewModel model;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView score;
        if(item.getItemId() == R.id.item_1){
            AlertDialog.Builder builder = new AlertDialog.Builder(LeadershipBoardPage.this);
            builder.setMessage(getString(R.string.message))
                    .setTitle(getString(R.string.title2))
                    .setPositiveButton(getString((R.string.gotit)), ((dialog, clk) -> {
                        dialog.cancel();
                    }));

            builder.create().show();
        }else{
            model.selectedPlayer.postValue(ranking.get(position));
            score = findViewById(R.id.score);
            AlertDialog.Builder builder = new AlertDialog.Builder(LeadershipBoardPage.this);
            builder.setMessage(getString(R.string.deletemsg) + ranking.get(position).playerName)
                    .setTitle(getString(R.string.deltitle))
                    .setNegativeButton(getString(R.string.negBtn), ((dialog, which) -> {}))
                    .setPositiveButton(getString(R.string.posBtn), ((dialog, which) -> {
                        PlayerInformation removePlayer = ranking.get(position);
                        ranking.remove(position);
                        myAdapter.notifyItemRemoved(position);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(()->{
                            pDAO.deleteInformation(removePlayer);
                            runOnUiThread(()->variableBinding.recyclerView.setAdapter(myAdapter));
                        });
                        Snackbar.make(score, removePlayer.playerName+ getString(R.string.confirmdeletion), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), clk ->{
                                   ranking.add(position, removePlayer);
                                   myAdapter.notifyItemInserted(position);
                                }).show();
                    }));
            builder.create().show();
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Use for opening the database*/
        InformationDatabase db = Room.databaseBuilder(getApplicationContext(),
                InformationDatabase.class,"database-name").build();
        pDAO= db.cmDAO();

        ranking = new ArrayList<PlayerInformation>();
        Intent fromPrevious = getIntent();
        int counter = fromPrevious.getIntExtra("point", point);
        String user_name = fromPrevious.getStringExtra("username");
        model = new ViewModelProvider(this).get(ProjectViewModel.class);
        variableBinding = LeadershipDetailsLayoutBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        setSupportActionBar(variableBinding.myToolbar);

        model.selectedPlayer.observe(this, newPlayer->{
            if(newPlayer != null){
                PlayerDetailsFragment detailsFragment = new PlayerDetailsFragment(newPlayer);

                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.addToBackStack("");
                tx.replace(R.id.fragmentLocation, detailsFragment);
                tx.commit();
            }

        });

        /*Creating an object called player_info to contain the username and their points*/
        PlayerInformation player_info = new PlayerInformation(user_name, String.valueOf(counter));

        Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                /*inserting into the database*/
                pDAO.insertInformation(player_info);
                ranking.addAll(pDAO.getAllPlayerInfo());
                runOnUiThread(()->{
                    variableBinding.recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyItemInserted(ranking.size()-1);
                        }
                );
            });

       /*Use to specify a single column scrolling in a vertical direction*/
        variableBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*Initializing the variable myAdapter*/
        variableBinding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /*Returns an int to indicate which layout to load*/
            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @NonNull
            /*Function creates ViewHolder Object. Represents a single row in the list*/
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                /*Loading the xml for the scores*/
                ScoreListBinding variableBinding = ScoreListBinding.inflate(getLayoutInflater());
                return new MyRowHolder(variableBinding.getRoot());
            }

            /*Function used to initialize a ViewHolder to go at the row specified at the position parameter*/
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                PlayerInformation player_info = ranking.get(position);
                    holder.rank.setText(String.valueOf(position + 1));
                    holder.score.setText(player_info.playerName);
                    holder.highscore.setText(player_info.counter);
            }

            /*Function returns an int specifying how many items to draw.
            * It returns the number of rows in the list. In this case, it will be returning
            * whatever is in the ArrayList aka the size of the list.
            * */
            @Override
            public int getItemCount() {
                return ranking.size();
            }
        });
    }

    /**
     * Class represents an object for representing everything that goes on a row in a list.
     * It will hold the reference to the TextView from the xml layer while displaying the rank, score, and highscore
     */
    class MyRowHolder extends RecyclerView.ViewHolder{
        /**
         * rank is the rank of the player starting from 1 and ending at 10. The rank is decided by the highscore.
         * score is the name of the player that was written after the user finished playing the game
         * highschore is the total score the user have received after finishing the game. It can only contain whole numbers
         */
        TextView rank;
        TextView score;
        TextView highscore;

        /**
         * The Constructor for the MyRowHolder Class where the viewholder where it is used to listen for a user to click on one of
         * the item on the list to display its specific information regarding the individual.
         * @param itemView is the view that represents a single item.
         */
        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk ->{
                position = getAbsoluteAdapterPosition();
                model.selectedPlayer.postValue(ranking.get(position));
            });
            /*Initialization of the textView fields*/
            rank = itemView.findViewById(R.id.rank);
            score = itemView.findViewById(R.id.score);
            highscore = itemView.findViewById(R.id.highscore);

        }
    }

}
