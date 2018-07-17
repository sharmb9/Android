package com.myappcompany.user.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebURLS= new ArrayList<>();
    ArrayList<String> celebNames= new ArrayList<>();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}



