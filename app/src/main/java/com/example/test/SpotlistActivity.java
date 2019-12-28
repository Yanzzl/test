package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpotlistActivity extends AppCompatActivity {
    SqliteHelper dbHelper;
    boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotlist);

        ListView listView = (ListView) findViewById(R.id.spotList);
        dbHelper = new SqliteHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getListContents();

        if (data.getCount() == 0){
            Toast.makeText(SpotlistActivity.this, "DB is empty.", Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(SpotlistActivity.this, "Fuck", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SpotlistActivity.this, ElevatorActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }













        //TODO original working spotlist
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_spotlist);
//
//        Button add = findViewById(R.id.addSpot_s);
//        ListView listView = (ListView) findViewById(R.id.spotList_sl);
//        dbHelper = new SqliteHelper(this);
//        if (dbHelper.isLogin()) {
//            isAdmin = dbHelper.isAdmin(dbHelper.getCurrent());
//        }
//        ArrayList<String> theList = new ArrayList<>();
//        Cursor data = dbHelper.getGeoPoints();
//
//        add.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SpotlistActivity.this, Upload.class);
//                startActivity(intent);
//            }
//        });
//
//        while (data.moveToNext()) {
//            theList.add(data.getString(1));
//        }
//        if (isAdmin) {
//            add.setVisibility(View.VISIBLE);
//            //instantiate custom adapter
//            MyCustomAdapter adapter = new MyCustomAdapter(theList, this);
//            //handle listview and assign adapter
//            listView.setAdapter(adapter);
//
//        } else {
//            ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.list_item_normal, theList);
//            listView.setAdapter(listAdapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String selectedItem = (String) parent.getItemAtPosition(position);
//                    //TODO elevator stuff
//                    Intent intent = new Intent(SpotlistActivity.this, MapsActivity.class);
//                    intent.putExtra("TITLE", selectedItem);
//                    intent.putExtra("POP_UP", true);
//                    startActivity(intent);
//                }
//            });
//        }

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finishAffinity();
        startActivity(intent);
    }


}
