package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpotlistActivity extends AppCompatActivity {
    SqliteHelper dbHelper;

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
                        intent.putExtra("Key", "Value");
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
