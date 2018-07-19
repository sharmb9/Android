package com.myappcompany.user.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    ArrayList<String> celebURLS= new ArrayList<>();
    ArrayList<String> celebNames= new ArrayList<>();

    String[] answers= new String[4];

    int chosenCelebIndex;
    int correctLocation;

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class DownloadContent extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result= "";
            URL url;
            HttpURLConnection connection;



            try {
                //get the url first
                url = new URL(urls[0]);

                //make a connection
                connection= (HttpURLConnection) url.openConnection();

                connection.connect();

                //download content

                //get the input
                InputStream in = connection.getInputStream();

                InputStreamReader reader= new InputStreamReader(in);

                int data= reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
    }

    public void newQuestion() {
        try {
            Random rand = new Random();

            //random index of a celebrity from celebURLS
            chosenCelebIndex = rand.nextInt(celebURLS.size());

            Log.i("Chosen celeb", celebNames.get(chosenCelebIndex));

            ImageDownloader imageTask = new ImageDownloader();

            //get a random celeb from celebURLS
            Bitmap celebImage = imageTask.execute(celebURLS.get(chosenCelebIndex)).get();

            imageView.setImageBitmap(celebImage);

            //declaring a random correct option from 0 to 3
            correctLocation= rand.nextInt(4);

            int incorrectAnswerLocation;


            for (int i=0; i<4; i++){

                //if index is the correct option, correct answer is the chosen celebrity
                if (i== correctLocation){
                    answers[i]= celebNames.get(chosenCelebIndex);
                }

                else {
                    incorrectAnswerLocation= rand.nextInt(celebNames.size());

                    while (incorrectAnswerLocation== chosenCelebIndex ){
                        incorrectAnswerLocation= rand.nextInt(celebNames.size());
                    }

                    answers[i]= celebNames.get(incorrectAnswerLocation);
                }

                Log.i("Celeb Array", String.valueOf(answers));



            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void optionPressed(View view){

        Log.i("Tag", (String) view.getTag());

        Log.i("Correct location", Integer.toString(correctLocation));

        if (view.getTag().toString().equals(Integer.toString(correctLocation)) ){
            Toast.makeText(this, "Correct ANswer!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Oops that's not correct!", Toast.LENGTH_SHORT).show();
        }
        newQuestion();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        button0= (Button) findViewById(R.id.button0);
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);



        DownloadContent task= new DownloadContent();
        String result= "";

        try {
            result= task.execute("http://www.posh24.se/kandisar").get();
            Log.i("Contents of URL", result);

            //Splitting the html code into two halves for easiness
            String[] html= result.split("div class=\"listedArticles\">");

            //url for each image
            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            //we just want the first half of url
            Matcher m = p.matcher(html[0]);

            //gets url for each image
            while(m.find()) {
                celebURLS.add(m.group(1));
            }

            //name of celebrity
            Pattern pp = Pattern.compile("alt=\"(.*?)\"");
            Matcher mm = pp.matcher(html[0]);

            while (mm.find()) {
                celebNames.add(mm.group(1));
            }

            newQuestion();

            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}



