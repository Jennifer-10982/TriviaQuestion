package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

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

        binding.submit.setOnClickListener(clk ->{
            if (answer == true){
                counter = counter + 30;
                variableBinding.points.setText(Integer.toString(counter));
            }else{
                counter = counter - 10;
                variableBinding.points.setText(Integer.toString(counter));
            };
        });
    }
}