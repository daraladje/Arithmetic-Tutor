package com.example.arithmetictutor;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import static android.widget.Toast.makeText;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;


//Questions
//Options above max or min?
//Division: no decimals? Subtraction, no negatives?


public class MainActivity extends AppCompatActivity {
    RadioButton easy, medium, hard;
    Button goButton;
    String selected = "";
    TextView settingsText;
    TextView timerText;
    TextView problemText;
    TextView correctText;
    TextView resultText;
    Button problemOne;
    Button problemTwo;
    Button problemThree;
    Button problemFour;
    Button playAgain;
    CountDownTimer countDownTimer;
    int[] numbers = new int[4];
    Random rand;
    int correct = 0;
    int total = 0;
    int maxInt = 10;
    int answer;
    int maxResults = 10;
    boolean notPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        easy = (RadioButton) findViewById(R.id.easy);
        medium = (RadioButton) findViewById(R.id.medium);
        hard =  (RadioButton)  findViewById(R.id.hard);
        goButton = (Button) findViewById(R.id.goButton);

        settingsText = (TextView) findViewById(R.id.settingsText);
        timerText = (TextView) findViewById(R.id.timerTextView);
        timerText.setVisibility(View.INVISIBLE);
        correctText = (TextView) findViewById(R.id.correctText);
        correctText.setVisibility(View.INVISIBLE);
        problemText = (TextView) findViewById(R.id.problemText);
        problemText.setVisibility(View.INVISIBLE);
        resultText = (TextView) findViewById(R.id.resultText);
        resultText.setVisibility(View.INVISIBLE);

        problemOne = (Button) findViewById(R.id.problemOne);
        problemOne.setVisibility(View.INVISIBLE);
        problemOne.setEnabled(false);
        problemTwo = (Button) findViewById(R.id.problemTwo);
        problemTwo.setVisibility(View.INVISIBLE);
        problemTwo.setEnabled(false);
        problemThree = (Button) findViewById(R.id.problemThree);
        problemThree.setVisibility(View.INVISIBLE);
        problemThree.setEnabled(false);
        problemFour = (Button) findViewById(R.id.problemFour);
        problemFour.setVisibility(View.INVISIBLE);
        problemFour.setEnabled(false);
        playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setVisibility(View.INVISIBLE);
        playAgain.setEnabled(false);

        //decided to just have one activity and change the visibility of certain components of the pap

    } //onCreate

    public void newGame(View view){
        //restart activity
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void beginProblems(View view) throws Exception{
        if( selected != "" ) { //only if user has selected a menu option
            notPlaying = false;
            settingsText.setVisibility(View.INVISIBLE);
            goButton.setVisibility(View.INVISIBLE);
            goButton.setEnabled(false);
            easy.setVisibility(View.INVISIBLE);
            easy.setEnabled(false);
            medium.setVisibility(View.INVISIBLE);
            medium.setEnabled(false);
            hard.setVisibility(View.INVISIBLE);
            hard.setEnabled(false);

            timerText.setVisibility(View.VISIBLE);
            correctText.setVisibility(View.VISIBLE);
            problemText.setVisibility(View.VISIBLE);

            problemOne.setVisibility(View.VISIBLE);
            problemOne.setEnabled(true);
            problemTwo.setVisibility(View.VISIBLE);
            problemTwo.setEnabled(true);
            problemThree.setVisibility(View.VISIBLE);
            problemThree.setEnabled(true);
            problemFour.setVisibility(View.VISIBLE);
            problemFour.setEnabled(true);

            setValues();

            countDownTimer = new CountDownTimer(60000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    timerText.setText(Integer.toString((int) millisUntilFinished / 1000) + "s");

                }

                @Override
                public void onFinish() {

                    resultText.setVisibility(View.INVISIBLE);
                    timerText.setVisibility(View.INVISIBLE);
                    correctText.setVisibility(View.INVISIBLE);
                    problemText.setVisibility(View.INVISIBLE);

                    problemOne.setVisibility(View.INVISIBLE);
                    problemOne.setEnabled(false);
                    problemTwo.setVisibility(View.INVISIBLE);
                    problemTwo.setEnabled(false);
                    problemThree.setVisibility(View.INVISIBLE);
                    problemThree.setEnabled(false);
                    problemFour.setVisibility(View.INVISIBLE);
                    problemFour.setEnabled(false);

                    playAgain.setVisibility(View.VISIBLE);
                    playAgain.setEnabled(true);

                }
            }.start();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please select a menu option",Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAnswer(View view) throws Exception{
        Button b = (Button)view;
        String guess = b.getText().toString();

        if( Integer.parseInt(guess) == answer )
        {
            resultText.setText("Correct");
            correct++;
        }
        else
        {
            resultText.setText("Wrong");
        }
        resultText.setVisibility(View.VISIBLE);
        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() { //Wrong and Correct only appearing for a brief period of time
                runOnUiThread(new Runnable() {
                    public void run() {
                        resultText.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 800);
        total++;
        correctText.setText(correct + "/" +total);
        setValues(); //set values for next question
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.operations, menu);
        return  super.onCreateOptionsMenu(menu);

    }//onCreateOptionsMenu


    public boolean onOptionsItemSelected (MenuItem item) {
        if(notPlaying) {
            switch (item.getItemId()) {

                case R.id.plus:
                    selected = "+";
                    break;
                case R.id.minus:
                    selected = "-";
                    break;
                case R.id.mult:
                    selected = "*";
                    break;
                case R.id.div:
                    selected = "/";
                    break;

            }//switch
        }
        return true;

    }//onOptionsItemSelected

    public void onRadioButtonClicked(View view) {
        switch(view.getId()) {
            case R.id.easy:
                maxInt = 10;
                break;
            case R.id.medium :
                maxInt = 20;
                break;
            case R.id.hard :
                maxInt = 50;
                break;
        }//switch
    }//onRadio

    public void setValues() throws Exception {
        try {

        for (int i = 0; i < 4; i++) {
            numbers[i] = -1;
        }
        int left = rand.nextInt(maxInt);
        int right = rand.nextInt(maxInt);
        switch (selected) {
            case "+":
                answer = left + right;
                maxResults = maxInt*2+1; //the max number that can be in the possible answers (to keep it realistic)
                break;
            case "-":
                int temp = left;
                left = Math.max(temp, right);
                right = Math.min(temp, right);
                answer = left - right;
                maxResults = left+2;
                break;
            case "*":
                answer = left * right;
                maxResults = maxInt*maxInt+1;
                break;
            case "/":
                answer = left * right;
                left = answer;
                answer /= right;
                maxResults = Math.max(left, right) + 2;
                break;
        }
        ;
        int correctIndex = rand.nextInt(4);
        numbers[correctIndex]= answer;
        int randNum = -1;
        Set<Integer> check = new HashSet<>(); //so two options are not repeated on the buttons
        check.add(-1);
        check.add(answer);

        for(int j = 0; j < numbers.length; j++) {
            if (numbers[j] == -1  )
            {
                while( check.contains(randNum) ) randNum = rand.nextInt(maxResults);
                numbers[j] = randNum;
                check.add(randNum);
            }
        }

        problemOne.setText(String.valueOf(numbers[0]));
        problemTwo.setText(String.valueOf(numbers[1]));
        problemThree.setText(String.valueOf(numbers[2]));
        problemFour.setText(String.valueOf(numbers[3]));

        problemText.setText(left + " " + selected + " " + right);

        } catch( Exception e ) {
            e.printStackTrace();
        }

    }

} //MainActivity
