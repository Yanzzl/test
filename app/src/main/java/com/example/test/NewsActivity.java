package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    SqliteHelper dbHelper;
    boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Button add = findViewById(R.id.addNews_news);
        ListView listView = (ListView) findViewById(R.id.list_news);
        dbHelper = new SqliteHelper(this);
        if (dbHelper.isLogin()) {
            isAdmin = dbHelper.isAdmin(dbHelper.getCurrent());
        }
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getNews();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, MakeNews.class);
                startActivity(intent);
            }
        });
        if (data.getCount() == 0) {
            Toast.makeText(NewsActivity.this, "No data", Toast.LENGTH_SHORT).show();

        }
        while (data.moveToNext()) {
            theList.add(data.getString(1));
        }
        if (isAdmin) {
            add.setVisibility(View.VISIBLE);
        }
        //instantiate custom adapter
        NewsCardAdapter adapter = new NewsCardAdapter(theList, this, isAdmin);
        //handle listview and assign adapter
        listView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finishAffinity();
        startActivity(intent);
    }

}
