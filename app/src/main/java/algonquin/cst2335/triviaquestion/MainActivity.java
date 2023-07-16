package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Reference: https://code.tutsplus.com/how-to-add-a-dropdown-menu-in-android-studio--cms-37860t
        * */
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence>myAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(myAdapter);


    }
}