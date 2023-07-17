package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.triviaquestion.databinding.ActivityMainBinding;
import algonquin.cst2335.triviaquestion.databinding.ActivityQuestionnairePageBinding;
import algonquin.cst2335.triviaquestion.databinding.ListItemBinding;

public class Questionnaire_Page extends AppCompatActivity {
    int counter = 0;
    boolean answer;

    private QuestionPageViewModel model;
    private ListItemBinding variableBinding;

    private ActivityQuestionnairePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_page);

        model = new ViewModelProvider(this).get(QuestionPageViewModel.class);
        binding= ActivityQuestionnairePageBinding.inflate(getLayoutInflater());
        variableBinding = ListItemBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        setContentView(binding.getRoot());

        variableBinding.points.setText(model.display_points);

        /**Checks whether the answer is correct. If it is, then the points increase. If not, then points decrease.
         * A message is display to notify whether the answer is correct or not.*/
        binding.submit.setOnClickListener(clk ->{
            if (answer == true){
                counter = counter + 30;
                /**Converting the integer into a String*/
                variableBinding.points.setText(Integer.toString(counter));
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            }else{
                counter = counter - 10;
                variableBinding.points.setText(Integer.toString(counter));
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            };
        });
    }
}