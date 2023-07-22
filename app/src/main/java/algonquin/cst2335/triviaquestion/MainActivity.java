package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import algonquin.cst2335.triviaquestion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private QuestionPageViewModel model;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /*
        * Reference: https://code.tutsplus.com/how-to-add-a-dropdown-menu-in-android-studio--cms-37860t
        * */
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence>myAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(myAdapter);

        model = new ViewModelProvider(this).get(QuestionPageViewModel.class);


        binding.playBtn.setOnClickListener(clk ->{
            Intent nextPage = new Intent(MainActivity.this, Questionnaire_Page.class);
            nextPage.putExtra("choice", spinner.getSelectedItem().toString());
            startActivity(nextPage);
        });


    }
}