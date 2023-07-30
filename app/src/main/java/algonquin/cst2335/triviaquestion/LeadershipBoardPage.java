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

public class LeadershipBoardPage extends AppCompatActivity {
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
        }else{
            model.selectedPlayer.postValue(ranking.get(position));
            score = findViewById(R.id.score);
            AlertDialog.Builder builder = new AlertDialog.Builder(LeadershipBoardPage.this);
            builder.setMessage("Are You Sure You Want to Delete This Champion: " + ranking.get(position).playerName)
                    .setTitle("WARNING: ")
                    .setNegativeButton("Go Back", ((dialog, which) -> {}))
                    .setPositiveButton("Confirm", ((dialog, which) -> {
                        PlayerInformation removePlayer = ranking.get(position);
                        ranking.remove(position);
                        myAdapter.notifyItemRemoved(position);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(()->{
                            pDAO.deleteInformation(removePlayer);
                            runOnUiThread(()->variableBinding.recyclerView.setAdapter(myAdapter));
                        });
                        Snackbar.make(score, removePlayer.playerName+"Has Been Eliminated", Snackbar.LENGTH_LONG)
                                .setAction("Undo", clk ->{
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

//        /*Use to specify a single column scrolling in a vertical direction*/
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

    /*Class represents an object for representing everything that goes on a row in a list*/
    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView rank;
        TextView score;
        TextView highscore;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk ->{
                position = getAbsoluteAdapterPosition();
                model.selectedPlayer.postValue(ranking.get(position));
            });

            rank = itemView.findViewById(R.id.rank);
            score = itemView.findViewById(R.id.score);
            highscore = itemView.findViewById(R.id.highscore);

        }
    }

}
