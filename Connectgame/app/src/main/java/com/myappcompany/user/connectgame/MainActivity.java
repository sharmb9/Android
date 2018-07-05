package com.myappcompany.user.connectgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // 1:yellow 2:red 0:none
    int activePlayer= 1;
    int[] gameStatus= {0,0,0,0,0,0,0,0,0};
    int[][] winCombos= {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,5}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameOver= false;


    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        Log.i("Tag", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        counter.setTranslationY(-1500);

        if (activePlayer==1 && gameOver==false){
            counter.setImageResource(R.drawable.yellow);
            counter.animate().translationY(0).setDuration(200);
            gameStatus[tappedCounter]=activePlayer;
            activePlayer= 2;

            Log.i("gameStatus", Arrays.toString(gameStatus));
        }

        else if (activePlayer==2 && gameOver==false){
            counter.setImageResource(R.drawable.red);
            counter.animate().translationY(0).setDuration(200);
            gameStatus[tappedCounter]=activePlayer;
            activePlayer=1;

            Log.i("gameStatus", Arrays.toString(gameStatus));
        }

        for (int[] winCombo: winCombos){
            if (gameStatus[winCombo[0]]==gameStatus[winCombo[1]] && gameStatus[winCombo[1]]==gameStatus[winCombo[2]] && gameStatus[winCombo[0]] != 0){
                gameOver= true;
                //we have a winner
                String winner;

                //carefull while deciding activePlayer since we change it in conditional statements
                if (activePlayer==2){
                    winner= "Yellow";
                }
                else{
                    winner= "Red";
                }


                TextView winnerText =(TextView) findViewById(R.id.winnerTextView);
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                //shows the winner and play again button
                winnerText.setText(winner + " has won");

                winnerText.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }


    }

    public void playAgain(View view){
        TextView winnerText =(TextView) findViewById(R.id.winnerTextView);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        winnerText.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView)gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i =0; i<gameStatus.length; i++){
            gameStatus[i]= 0;
        }
        activePlayer=1;
        gameOver= false;

        Log.i("Status:", "Game reset");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
