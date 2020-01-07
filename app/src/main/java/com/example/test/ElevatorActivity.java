package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ElevatorActivity extends AppCompatActivity {
    Button btnViewGame;
    Button btnViewMap;
    String spotTitle;

    Button btnViewGame2;
    Button btnViewMap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator);

        spotTitle = getIntent().getStringExtra("TITLE");

        //TODO added
//        Intent intent = getIntent();
//        String p1 = intent.getStringExtra("Key");
//
        btnViewGame = (Button) findViewById(R.id.door_01);

        btnViewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorActivity.this, game.class);
                startActivity(intent);
            }
        });

        btnViewMap = (Button) findViewById(R.id.door_02);

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorActivity.this, MapsActivity.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("POP_UP", true);
                startActivity(intent);
            }
        });

        btnViewGame2 = (Button) findViewById(R.id.door_03);

        btnViewGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorActivity.this, game.class);
                startActivity(intent);
            }
        });

        btnViewMap2 = (Button) findViewById(R.id.door_04);

        btnViewMap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
