package com.myappcompany.user.webviews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles= new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task= new DownloadTask();

        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        }catch (Exception e){
            e.printStackTrace();
        }

        listView= findViewById(R.id.listView);
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

    }

    //takes a string returns a string
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            //All the article IDs from the URL
            String result= "";

            URLConnection connection= null;

            try {
                url= new URL(urls[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream in = connection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);

                int data= reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data= reader.read();
                }

                //puts all the article ids into a json array
                JSONArray jsonArray= new JSONArray(result);

                //we just wanna access first 20 articles for now
                 int numberOfItems= 20;

                 //incase number of articles(length of the json array) are less than 20
                if (numberOfItems<jsonArray.length()){
                    numberOfItems= jsonArray.length();
                }



                //now we will puyt each article id into a url which loads the api for individual article ("https://hacker-news.firebaseio.com/v0/item/+ articleID" + ".json?print=pretty")
                for (int i = 0; i<numberOfItems; i++){

                    //the json object we working on(in this case the first article id in articleID JSON array)
                    String articleID= jsonArray.getString(i);

                    //now we need to load the the url for the specific article
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/"+ articleID + ".json?print=pretty");
                    connection= url.openConnection();
                    connection.connect();

                    //getting the html of the page
                    in= connection.getInputStream();
                    reader= new InputStreamReader(in);
                    data= reader.read();

                    String articelInfo= "";

                    while (data != -1){
                        char current = (char) data;
                        articelInfo += current;
                        data= reader.read();
                    }

                    Log.i("Articel Info for" + articleID + " : ", articelInfo);

                    //converting the articelInfo string to JSONObject
                    JSONObject jsonObject= new JSONObject(articelInfo);


                    //Also making sure title and url are not null
                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url")){
                        String articleTitle= jsonObject.getString("title");
                        final String articelURl= jsonObject.getString("url");
                        titles.add(articleTitle);
                        arrayAdapter.notifyDataSetChanged();
                        /*url= new URL(articelURl);
                        connection= url.openConnection();
                        connection.connect();*/

                        //loads the url and opens it in a new activity on clicking the news title
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent= new Intent(getApplicationContext(), NewsActivity.class);
                                intent.putExtra("NewsURL", articelURl);
                                startActivity(intent);

                            }
                        });

                    }


                }


                Log.i("Articles: ", result);
                return result;

            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


    }

}
