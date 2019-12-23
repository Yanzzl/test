package com.example.test.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.MapsActivity;
import com.example.test.PopUpWindow;
import com.example.test.R;
import com.example.test.SqliteHelper;

import java.util.ArrayList;

public class Liked extends AppCompatActivity {

    SqliteHelper dbHelper;
    String currentUser;
    ArrayList<String> favoriteSpots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);
        dbHelper = new SqliteHelper(this);

//        TextView liked = findViewById(R.id.boortoren);
        ListView listView = (ListView) findViewById(R.id.liked_spots_l);

        currentUser = dbHelper.getCurrent();

        Cursor data = dbHelper.getFavoriteSpots(currentUser);
        while(data.moveToNext()){
            favoriteSpots.add(data.getString(2));
            ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteSpots);
            listView.setAdapter(listAdapter);
        }

        //TODO
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(Liked.this, MapsActivity.class);
                intent.putExtra("TITLE", selectedItem);
                intent.putExtra("POP_UP", true);
                startActivity(intent);
            }
        });
//        if (userData.isLogin()) {
//            String current = userData.getCurrentUser();
//            if (userData.isStared(current)) {
//                liked.setVisibility(View.VISIBLE);
//            }
//        }

//        liked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Liked.this, MapsActivity.class);
//                intent.putExtra("POP_UP_BOORTOREN", true);
//                startActivity(intent);
//            }
//        });
    }



}
