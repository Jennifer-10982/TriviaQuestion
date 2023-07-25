package algonquin.cst2335.triviaquestion;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.triviaquestion.databinding.LeadershipDetailsLayoutBinding;

public class LeadershipBoardPage extends AppCompatActivity {
    private LeadershipDetailsLayoutBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = LeadershipDetailsLayoutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.leadership_details_layout);

        variableBinding.recyclerView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            /*Function creates ViewHolder Object. Represents a single row in the list*/
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyRowHolder(variableBinding.getRoot());
            }

            /*Funtion used to initialize a ViewHolder to go at the row specified at the position parameter*/
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {

            }

            /*Function returns an int specifying how many items to draw*/
            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    /*Class represents an object for representing everything that goes on a row in a list*/
    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView position;
        TextView score;
        TextView highscore;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            position = itemView.findViewById(R.id.place);
            score = itemView.findViewById(R.id.score);
            highscore = itemView.findViewById(R.id.highscore);

        }
    }

}
