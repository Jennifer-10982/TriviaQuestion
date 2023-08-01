/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description: The class MainActivity is the welcome page of the app where the user will have an option to choose 1 of 5 categories to play with.
 * */
package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import algonquin.cst2335.triviaquestion.databinding.ActivityMainBinding;

/**
 * The class MainActivity is the welcome page of the app where the user will have an option to choose 1 of 5 categories to play with.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * model is used to prepare and managed data for Questionnaire_Page and handles communication between the class. It is the business logic.
     * variableBinding is the binding class that represents the layout xml used and allow access for said xml variable
     */
    private ProjectViewModel model;

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

        model = new ViewModelProvider(this).get(ProjectViewModel.class);

        binding.playBtn.setOnClickListener(clk ->{
            Intent nextPage = new Intent(MainActivity.this, Questionnaire_Page.class);
            nextPage.putExtra("choice", spinner.getSelectedItem().toString());
            startActivity(nextPage);
        });


    }
}