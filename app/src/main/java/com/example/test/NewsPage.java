package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsPage extends AppCompatActivity {
    SqliteHelper dbHelper;
    String title;
    TextView titleView;
    TextView descriptionView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        dbHelper = new SqliteHelper(this);
        title = getIntent().getStringExtra("TITLE");

        titleView = findViewById(R.id.title_np);
        descriptionView = findViewById(R.id.description_np);
        imageView = findViewById(R.id.image_np);


        titleView.setText(title);
        fillContent();

    }

    public void fillContent(){
        Cursor data = dbHelper.getNews(title);
        if (data.getCount() == 0) {
            Toast.makeText(NewsPage.this, "Load failed", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                descriptionView.setText(data.getString(2));
                imageView.setImageURI(Uri.parse(data.getString(3)));
            }
        }
    }
}
