package com.myappcompany.user.braingame;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView timeView;
    TextView promptView;
    TextView scoreView;
    GridLayout gridView;
    TextView messageView;

    ArrayList optionsArray =new ArrayList();

    Button option1;
    Button option2;
    Button option3;
    Button option4;

    int locationOfCorrectAns;

    int queCount;
    int correctCount;

    boolean gameOver;

    Button playAgainButton;



    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);

        timeView.setVisibility(View.VISIBLE);
        promptView.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.VISIBLE);
    }

    public void gameScreen(){

        Random randInt= new Random();
        //setting up random integer for question
        int a= randInt.nextInt(20); // a and b are some random int between 0 and 20
        int b= randInt.nextInt(20);

        //setting up question
        promptView.setText(Integer.toString(a) + "+" + Integer.toString(b));
        locationOfCorrectAns= randInt.nextInt(4);



        //setting up options in a array
        for (int i=0; i<4; i++){
            int correctAns= a+b;
            int randomOption= randInt.nextInt(40); //so that sum can go upto sum of max range, i.e. 20+20
            if (i==locationOfCorrectAns){
                optionsArray.set(i,correctAns); // the correct answer
            }
            else {
                while (randomOption==a+b){
                    randomOption= randInt.nextInt(40);
                }
                optionsArray.set(i, randomOption);
            }
        }

        //setting up options into buttons
        option1.setText(Integer.toString((Integer) optionsArray.get(0)));
        option2.setText(Integer.toString((Integer) optionsArray.get(1)));
        option3.setText(Integer.toString((Integer) optionsArray.get(2)));
        option4.setText(Integer.toString((Integer) optionsArray.get(3)));
    }

    public void optionClicked(View view){
        //view here is the button clicked

        //question count is 10

        while (gameOver==false){

            queCount++;


            Log.i("Option", view.getTag().toString());
            Log.i("Correct option", Integer.toString(locationOfCorrectAns));

            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAns) )){
                correctCount++;
                Log.i("Result", "Correct");
                messageView.setText("Correct!");
                scoreView.setText(String.valueOf(correctCount) + "/" + String.valueOf(queCount));
            }
            else{
                messageView.setText("Wrong!");
                Log.i("Result", "Wrong");
                scoreView.setText(String.valueOf(correctCount) + "/" + String.valueOf(queCount));
            }

            if (queCount==10){
                gameOver=true;
                playAgainButton.setVisibility(View.VISIBLE);
            }

        }

    }

    public void playAgain(View view){
        queCount=0;
        correctCount=0;

        //setting up random integer for question
        Random randInt= new Random();
        int a= randInt.nextInt(20); // a and b are some random int between 0 and 20
        int b= randInt.nextInt(20);

        //setting up question
        promptView.setText(Integer.toString(a) + "+" + Integer.toString(b));

        locationOfCorrectAns= randInt.nextInt(4);

        //setting up options in a array
        for (int i=0; i<4; i++){
            int correctAns= a+b;
            int randomOption= randInt.nextInt(40); //so that sum can go upto sum of max range, i.e. 20+20
            if (i==locationOfCorrectAns){
                optionsArray.add(correctAns); // the correct answer
            }
            else {
                while (randomOption==a+b){
                    randomOption= randInt.nextInt(40);
                }
                optionsArray.add(randomOption);
            }
        }

        //setting up options into buttons
        option1.setText(Integer.toString((Integer) optionsArray.get(0)));
        option2.setText(Integer.toString((Integer) optionsArray.get(1)));
        option3.setText(Integer.toString((Integer) optionsArray.get(2)));
        option4.setText(Integer.toString((Integer) optionsArray.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        timeView = (TextView) findViewById(R.id.timeView);
        promptView =(TextView) findViewById(R.id.promptView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        gridView = (GridLayout) findViewById(R.id.gridView);
        messageView = (TextView) findViewById(R.id.messageView);



        option1= (Button) findViewById(R.id.option1);
        option2= (Button) findViewById(R.id.option2);
        option3= (Button) findViewById(R.id.option3);
        option4= (Button) findViewById(R.id.option4);


        gameOver= false;

        playAgainButton= (Button) findViewById(R.id.playAgainButton);

        queCount=0;
        correctCount=0;

        //setting up random integer for question
        Random randInt= new Random();
        int a= randInt.nextInt(20); // a and b are some random int between 0 and 20
        int b= randInt.nextInt(20);

        //setting up question
        promptView.setText(Integer.toString(a) + "+" + Integer.toString(b));

        locationOfCorrectAns= randInt.nextInt(4);

        //setting up options in a array
        for (int i=0; i<4; i++){
            int correctAns= a+b;
            int randomOption= randInt.nextInt(40); //so that sum can go upto sum of max range, i.e. 20+20
            if (i==locationOfCorrectAns){
                optionsArray.add(correctAns); // the correct answer
            }
            else {
                while (randomOption==a+b){
                    randomOption= randInt.nextInt(40);
                }
                optionsArray.add(randomOption);
            }
        }

        //setting up options into buttons
        option1.setText(Integer.toString((Integer) optionsArray.get(0)));
        option2.setText(Integer.toString((Integer) optionsArray.get(1)));
        option3.setText(Integer.toString((Integer) optionsArray.get(2)));
        option4.setText(Integer.toString((Integer) optionsArray.get(3)));


    }
}
