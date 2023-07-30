package algonquin.cst2335.triviaquestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
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

    QuestionPageViewModel playerModel;

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

        variableBinding = LeadershipDetailsLayoutBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        /*Creating an object called player_info to contain the username and their points*/
        PlayerInformation player_info = new PlayerInformation(user_name, String.valueOf(counter));
        /*Adding the object player_info into the ArrayList*/
        ranking.add(player_info);

        Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                pDAO.insertInformation(player_info);
                ranking.addAll(pDAO.getAllPlayerInfo());
                runOnUiThread(()->{
                    variableBinding.recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyItemInserted(ranking.size()-1);
                        }

                );
                /*Notifies the adapter that a new item has been inserted into the dataset and should
                 * update the recyclerView*/
//                myAdapter.notifyItemInserted(ranking.size()-1);
                /*Is called whenever the entire ArrayList has changed (like loading from the database)*/
//                myAdapter.notifyDataSetChanged();
            });

//        /*Notifies the adapter that a new item has been inserted into the dataset and should
//         * update the recyclerView*/
//        myAdapter.notifyItemInserted(ranking.size()-1);
//        /*Is called whenever the entire ArrayList has changed (like loading from the database)*/
//        myAdapter.notifyDataSetChanged();

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
            rank = itemView.findViewById(R.id.rank);
            score = itemView.findViewById(R.id.score);
            highscore = itemView.findViewById(R.id.highscore);

        }
    }

}
