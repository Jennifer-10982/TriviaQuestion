package algonquin.cst2335.triviaquestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algonquin.cst2335.triviaquestion.databinding.ListItemBinding;

public class Questionnaire_Page extends AppCompatActivity implements View.OnClickListener {
    int counter = 0;
    String answer = null;

    private ProjectViewModel model;
    private ListItemBinding variableBinding;
    protected RequestQueue queue = null;
    int number;
    int i = 0;

    String stringURL, question, correct_answer;
    TextView option1, option2, option3, option4;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_1){
            AlertDialog.Builder builder = new AlertDialog.Builder(Questionnaire_Page.this);
            builder.setMessage(getString(R.string.message))
                    .setTitle(getString(R.string.title2))
                    .setPositiveButton(getString((R.string.gotit)), ((dialog, clk) -> {
                        dialog.cancel();
                    }));

            builder.create().show();
        }else
            Toast.makeText(Questionnaire_Page.this, getString(R.string.denyAction), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Creating a Volley Object that will connect to the server*/
        queue = Volley.newRequestQueue(this);
//        setContentView(R.layout.activity_questionnaire_page);

        variableBinding = ListItemBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        setSupportActionBar(variableBinding.myToolbar);

        number = convertCategoryToNumber();

            try {
                stringURL = new StringBuilder()
                        .append("https://opentdb.com/api.php?amount=10&category=")
                        .append(URLEncoder.encode(String.valueOf(number), "UTF-8"))
                        .append("&type=multiple")
                        .append("&appid=7e943c97096a9784391a981c4d878b22").toString();
            } catch(UnsupportedEncodingException e){e.printStackTrace();}

            JsonObjectRequest request;

            /*Retrieving the values until*/
        request = loadPage(stringURL);
        queue.add(request);

        option1 = variableBinding.option1;
        option2 = variableBinding.option2;
        option3 = variableBinding.option3;
        option4 = variableBinding.option4;

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        model = new ViewModelProvider(this).get(ProjectViewModel.class);
        variableBinding.points.setText(String.valueOf(model.display_points));

        /*Checks whether the answer is correct. If it is, then the points increase. If not, then points decrease.
         * A message is display to notify whether the answer is correct or not.*/
        variableBinding.submit.setOnClickListener(clk ->{
            checker();
        });
    }

    public int convertCategoryToNumber(){
        Intent fromPrevious = getIntent();
        /*Retrieves the selection from the Main Activity*/
        String choice = fromPrevious.getStringExtra("choice");

        String[] categories = getResources().getStringArray(R.array.categories);
        if (choice.equalsIgnoreCase(categories[0])){
            return number = 22;
        } else if (choice.equalsIgnoreCase(categories[1])){
            return number = 20;
        } else if (choice.equalsIgnoreCase(categories[2])){
            return number = 23;
        }else if (choice.equalsIgnoreCase(categories[3])){
            return number = 24;
        }else
            return number = 25;
        }

        /*Checks whether the user's choice was right or wrong*/
    public void checker(){
       if(i>=0){
           variableBinding.submit.setOnClickListener(clk ->{
               Intent nextPage = new Intent(Questionnaire_Page.this, GameOverPage.class);
               nextPage.putExtra("counter", counter);
               startActivity(nextPage);
           });
       }
        if (answer.equals(correct_answer)){
            counter = counter + 30;
            i++;
           JsonObjectRequest request = loadPage(stringURL);
            queue.add(request);
            /*Converting the integer into a String*/
            variableBinding.points.setText(Integer.toString(counter));
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else{
            JsonObjectRequest request = loadPage(stringURL);
            queue.add(request);
            counter = counter - 10;
            variableBinding.points.setText(Integer.toString(counter));
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.option_1){
            answer = variableBinding.option1.getText().toString();
            changeColor(1);
        } else if (v.getId() == R.id.option_2) {
            answer = variableBinding.option2.getText().toString();
            changeColor(2);
        } else if (v.getId() == R.id.option_3) {
            answer = variableBinding.option3.getText().toString();
            changeColor(3);
        } else {
            answer = variableBinding.option4.getText().toString();
            changeColor(4);
        }
    }

    public void changeColor(int option){
        switch(option){
            case 1:
                variableBinding.option1.setTextColor(Color.parseColor("#FFC6CF6E"));
                variableBinding.option2.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option3.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option4.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;

            case 2:
                variableBinding.option1.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option2.setTextColor(Color.parseColor("#FFC6CF6E"));
                variableBinding.option3.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option4.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;

            case 3:
                variableBinding.option1.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option2.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option3.setTextColor(Color.parseColor("#FFC6CF6E"));
                variableBinding.option4.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;

            case 4:
                variableBinding.option1.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option2.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option3.setTextColor(Color.parseColor("#FFFFFFFF"));
                variableBinding.option4.setTextColor(Color.parseColor("#FFC6CF6E"));
                break;
        }
    }

    public JsonObjectRequest loadPage(String stringURL){
        return new JsonObjectRequest(Request.Method.GET, stringURL, null,
                (response) -> {
                    try{
                        JSONArray result = new JSONArray();
                        /*Using to retrieve the results from the database*/
                        JSONArray resultsArray = response.getJSONArray("results");

                        /*Use to retrieve a question object from the database*/
                        JSONObject questionObject = resultsArray.getJSONObject(0);

                        question = questionObject.getString("question");
                        correct_answer = questionObject.getString("correct_answer");
                        JSONArray incorrectAnsArray = questionObject.getJSONArray("incorrect_answers");

                        /*Converting the JsonArray incorrectAnsArray into a List
                         * Reference: https://www.javatpoint.com/how-to-convert-json-array-to-arraylist-in-java
                         * */
                        List<String> incorrectAnswers = new ArrayList<>();
                        if(incorrectAnsArray!=null){
                            for (int i = 0; i < incorrectAnsArray.length(); i++){
                                incorrectAnswers.add(incorrectAnsArray.getString(i));
                            }
                        }

                        /*Creating a list of all possible answers including incorrect and correct answers
                         * Reference: https://www.baeldung.com/java-copy-list-to-another
                         * */
                        List<String> answers = new ArrayList<>(incorrectAnswers);
                        answers.add(correct_answer);

                        /*Shuffling the list called answers*/
                        Collections.shuffle(answers);

                        runOnUiThread(()->{
                            /*Setting the objects from JSON to the buttons*/
                            variableBinding.quiz.setText(question);
                            /*Setting the text per option with the values in the array list that have been randomized*/
                            variableBinding.option1.setText(answers.get(0));
                            variableBinding.option2.setText(answers.get(1));
                            variableBinding.option3.setText(answers.get(2));
                            variableBinding.option4.setText(answers.get(3));
                        });

                    }catch(JSONException e){e.printStackTrace();}
                },
                (error )-> {});
    };
};
