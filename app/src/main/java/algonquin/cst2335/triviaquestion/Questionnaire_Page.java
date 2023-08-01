/*
 * Student Name: Jennifer Huynh
 * Student Number: 041086110
 * Lab Section: CST2335_022
 * Due Date: 07/08/23
 * Description:  Questionnaire_Page class is used to retrieve the questions from the database dependent on which the user chose as a category and
 * display the question, and the choices. If the user's choice is incorrect, 10 points is deducted. If correct, then user receives 30 points.
 * User must get 5 questions correct before proceeding to the next page
 * */
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

/**
 * Questionnaire_Page class is used to retrieve the questions from the database dependent on which the user chose as a category and
 * display the question, and the choices. If the user's choice is incorrect, 10 points is deducted. If correct, then user receives 30 points.
 * User must get 5 questions correct before proceeding to the next page
 */
public class Questionnaire_Page extends AppCompatActivity implements View.OnClickListener {
    /**
     * counter is a counter that stores points. It can only contain whole numbers
     * answer is the string value that the user has chosen among the 4 selection
     * model is used to prepare and managed data for Questionnaire_Page and handles communication between the class. It is the business logic
     * variableBinding is the binding class that represents the layout xml used and allow access for said xml variable
     * number is used to store the value of the code for the category. It can only contain whole numbers
     * i is a counter to be incremented starting from 0 to 10.
     * stringUrl is the url to the database
     * question is a string that contains the question from the database
     * correct_answer is a string that contains the correct answer taken from the database
     * option1 is being reference from TextView element from the layout using the data binding. It is one of the 4 option the user can click on. It will contain 1 of the 4 selection
     * option2 is being reference from TextView element from the layout using the data binding. It is one of the 4 option the user can click on. It will contain 1 of the 4 selection
     * option3 is being reference from TextView element from the layout using the data binding. It is one of the 4 option the user can click on. It will contain 1 of the 4 selection
     * option4 is being reference from TextView element from the layout using the data binding. It is one of the 4 option the user can click on. It will contain 1 of the 4 selection
     */
    int counter = 0;
    String answer = null;
    private ProjectViewModel model;
    private ListItemBinding variableBinding;
    protected RequestQueue queue = null;
    int number;
    int i = 0;

    String stringURL, question, correct_answer;
    TextView option1, option2, option3, option4;

    /**
     *Method onCreateOptionMenu is used to create a menu by retrieving the menu xml
     * @param menu The options menu in which you place your items.
     *
     * @return true indicating that the menu has been sucessfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * onOptionItemSelected is used to display the option from the menu. The following action will follow
     * dependent on which item the user chooses. item_1 displays the help/how to play menu. Item_2 indicates that the
     * following action (Delete) cannot be performed
     * @param item The menu item that was selected.
     *
     * @return true indicating that the menu has been sucessfully created
     */
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

    /**
     *  The method is used to convert the category that the user chose at the main activity into a number
     *  to be inserted into a link
     * @return number is an number that represents the category
     */
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

    /**
     * Checks whether the user's choice was right or wrong. If wrong, counter will decrease by 10 points.
     * If the answer is right, an additional 30 points will be added on. Once the user gets 5 correct answer,
     * another page will load.
     */
    public void checker(){
       if(i>=5){
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

    /**
     * Method used to retrieve and store the option that the user chose into the variable answer.
     * When choosing an option, it calls the method changeColor() to highlight the choice.
     * @param v The view that was clicked.
     */
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

    /**
     * Method is use to set/highlight the color the user have clicked on.
     * @param option is the option of which the user have selected amongst the other 4 options.
     */
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

    /**
     * Method is used to retrieve the apropriate question and the answer from the respected dataset from the database.
     * The 4 option will be randomly assign to the TextView options and displayed alongside the question.
     * @param stringURL  is the url to the database.
     * @return JsonObjectRequest contains the question and the apropriate selection from the database.
     */
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
