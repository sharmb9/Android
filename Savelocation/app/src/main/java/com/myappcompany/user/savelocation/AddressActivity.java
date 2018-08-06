package com.myappcompany.user.savelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    ListView listView;

    public void generateTable(){

        //Get Extra from previous activity (Address in this case)
        Intent intent= getIntent();
        String address= intent.getStringExtra("Address");

        ArrayList<String> addressArray = new ArrayList<String>();
        addressArray.add(address);

        //ArrayAdapter
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addressArray);

        //set adapter into list view
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        listView= findViewById(R.id.listView);

        generateTable();
    }
}
