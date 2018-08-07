package com.myappcompany.user.savelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> addressArray= new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        listView= findViewById(R.id.listView);

        //set the addresses into the adapter from the ArrayList
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addressArray);

        //set adapter into list view
        listView.setAdapter(arrayAdapter);

        //Get Extra from previous activity (Address in this case)
        Intent intent= getIntent();
        String address= intent.getStringExtra("Address");

        //add the address to the ArrayList
        addressArray.add(address);
    }
}
