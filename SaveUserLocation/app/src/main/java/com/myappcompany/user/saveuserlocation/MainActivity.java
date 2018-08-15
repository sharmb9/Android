package com.myappcompany.user.saveuserlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static ArrayList<String> address= new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listView);

        address.add("Add location...");

        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, address);
        listView.setAdapter(arrayAdapter);

        //On clicking the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Goes to MapsActivity on clicking the listView
                Intent intent= new Intent(getApplicationContext(), MapsActivity.class);

                startActivity(intent);
            }
        });
    }
}
