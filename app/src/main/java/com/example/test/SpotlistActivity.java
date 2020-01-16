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

        dbHelper = new SqliteHelper(this);

        ListView listView = (ListView) findViewById(R.id.spotList_sl);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getGeoPoints();
        while (data.moveToNext()) {
            theList.add(data.getString(1));
        }


//        Button add = findViewById(R.id.addSpot_s);
//        add.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SpotlistActivity.this, Upload.class);
//                startActivity(intent);
//            }
//        });
//        if (dbHelper.isLogin()) {
//            isAdmin = dbHelper.isAdmin(dbHelper.getCurrent());
//        }
//        if (isAdmin) {
//            add.setVisibility(View.VISIBLE);
//            //instantiate custom adapter
//            MyCustomAdapter adapter = new MyCustomAdapter(theList, this);
//            //handle listview and assign adapter
//            listView.setAdapter(adapter);
//
//        } else {
        ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.list_item_normal, theList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // original working spotlist
//                    Intent intent = new Intent(SpotlistActivity.this, MapsActivity.class);
//                    intent.putExtra("TITLE", selectedItem);
//                    intent.putExtra("POP_UP", true);
//                    startActivity(intent);
                Intent intent = new Intent(SpotlistActivity.this, ElevatorB1.class);
                intent.putExtra("TITLE", selectedItem);
                intent.putExtra("IS_LOGIN", false);
                startActivity(intent);

                // added
//                    Intent intent = new Intent(SpotlistActivity.this, ElevatorActivity.class);
//                    intent.putExtra("Key", "Value");
//                    startActivity(intent);
            }
        });
//        }

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finishAffinity();
        startActivity(intent);
    }


}