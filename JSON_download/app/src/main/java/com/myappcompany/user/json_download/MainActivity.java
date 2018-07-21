package com.myappcompany.user.json_download;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityText;
    Button checkButton;
    TextView resultText;


    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            String result="";
            HttpURLConnection connection;

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream in = connection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);
                int data= reader.read();
                while (data!= -1){
                    char current= (char) data;
                    result+=current;
                    data= reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
                return null;
            }

        }

        //this is more of a non-background task, you can make changes here
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject= new JSONObject(s);
                String weatherInfo= jsonObject.getString("weather");

                Log.i("Weather Info", weatherInfo);

                JSONArray jsonArray= new JSONArray(weatherInfo);

                String message="";

                for (int i=0; i< jsonArray.length(); i++){
                    JSONObject jsonPart= jsonArray.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description =jsonPart.getString("description");

                    //just to check that main and descriptions are not empty
                    if (!main.equals("") && !description.equals("")){
                        message+= main + ": " + description;
                    }
                }

                if (!message.equals("")){
                    resultText.setText(message);
                }

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkWeather(View view){

        String userCity= cityText.getText().toString();
        DownloadTask task= new DownloadTask();

        //Hides the keyboard
        InputMethodManager mgr= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromInputMethod(cityText.getWindowToken(),0);

        try {
            task.execute("https://openweathermap.org/data/2.5/weather?q=" + userCity + "&appid=b6907d289e10d714a6e88b30761fae22").get(); //basically executes the methods under DonloadTask

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText= findViewById(R.id.cityText);
        checkButton = (Button) findViewById(R.id.checkButton);
        resultText= (TextView) findViewById(R.id.resultText);



    }
}
