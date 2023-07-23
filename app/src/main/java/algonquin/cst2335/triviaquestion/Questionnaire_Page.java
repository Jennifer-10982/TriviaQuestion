package algonquin.cst2335.triviaquestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import algonquin.cst2335.triviaquestion.databinding.ActivityMainBinding;
import algonquin.cst2335.triviaquestion.databinding.ActivityQuestionnairePageBinding;
import algonquin.cst2335.triviaquestion.databinding.ListItemBinding;

public class Questionnaire_Page extends AppCompatActivity implements View.OnClickListener {
    int counter = 0;
    String answer = null;

    private QuestionPageViewModel model;
    private ListItemBinding variableBinding;

    protected RequestQueue queue = null;

//    private ActivityQuestionnairePageBinding binding;

    int number;
    int i;

    String question;
    String correct_answer;

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Creating a Volley Object that will connect to the server*/
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_questionnaire_page);

        variableBinding = ListItemBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        number = convertCategoryToNumber();
        String stringURL ="";
        /*---------------Start of loading Data into Page----------------------------*/
            try {
                stringURL = new StringBuilder()
                        .append("https://opentdb.com/api.php?amount=10&category=")
                        .append(URLEncoder.encode(String.valueOf(number), "UTF-8"))
                        .append("&type=multiple")
                        .append("&appid=7e943c97096a9784391a981c4d878b22").toString();
            } catch(UnsupportedEncodingException e){e.printStackTrace();}

//         for (i = 0; i <= 10; i++){
            /*Using JsonObjectRequest to retrieve JSON data from stringURL using Volley*/
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try{
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
            /*Adding HTTP request called request to the Volley request queue*/
            queue.add(request);
       // }
        /*---------------End of Loading Data into Page---------------------------------*/

        option1 = variableBinding.option1;
        option2 = variableBinding.option2;
        option3 = variableBinding.option3;
        option4 = variableBinding.option4;

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        model = new ViewModelProvider(this).get(QuestionPageViewModel.class);
        variableBinding.points.setText(String.valueOf(model.display_points));

        /*Checks whether the answer is correct. If it is, then the points increase. If not, then points decrease.
         * A message is display to notify whether the answer is correct or not.*/
        variableBinding.submit.setOnClickListener(clk ->{
            checker();
//            i++;
        });
    }

    public int convertCategoryToNumber(){
        Intent fromPrevious = getIntent();
        /*Retrieves the selection from the Main Activity*/
        String choice = fromPrevious.getStringExtra("choice");

        if (choice.equalsIgnoreCase("Geography")){
            return number = 22;
        } else if (choice.equalsIgnoreCase("Mythology")){
            return number = 20;
        } else if (choice.equalsIgnoreCase("History")){
            return number = 23;
        }else if (choice.equalsIgnoreCase("Politics")){
            return number = 24;
        }else
            return number = 25;
        }

        /*Checks whether the user's choice was right or wrong*/
    public void checker(){
        if (answer.equals(correct_answer)){
            counter = counter + 30;
            /*Converting the integer into a String*/
            variableBinding.points.setText(Integer.toString(counter));
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else{
            counter = counter - 10;
            variableBinding.points.setText(Integer.toString(counter));
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
    };

    /*Method use to store the selected choice*/
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.option_1){
            answer = variableBinding.option1.getText().toString();
            variableBinding.option1.setBackgroundColor(Color.parseColor("#86cc55"));
        } else if (v.getId() == R.id.option_2) {
            answer = variableBinding.option2.getText().toString();
            variableBinding.option1.setBackgroundColor(Color.parseColor("#86cc55"));
        } else if (v.getId() == R.id.option_3) {
            answer = variableBinding.option3.getText().toString();
            variableBinding.option1.setBackgroundColor(Color.parseColor("#86cc55"));
        } else
            answer = variableBinding.option4.getText().toString();
        variableBinding.option1.setBackgroundColor(Color.parseColor("#86cc55"));
    }
};
