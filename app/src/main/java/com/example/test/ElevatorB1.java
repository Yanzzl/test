package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ElevatorB1 extends AppCompatActivity {
    Button btnDown;
    TextView txtMap;

    String spotTitle;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_b1);

        btnDown = (Button) findViewById(R.id.btnDownF1);
        txtMap = (TextView) findViewById(R.id.txtMap);

        spotTitle = getIntent().getStringExtra("TITLE");
        isLogin = getIntent().getBooleanExtra("IS_LOGIN", false);

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB1.this, ElevatorB2.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("IS_LOGIN", isLogin);
                startActivity(intent);
            }
        });

        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB1.this, MapsActivity.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("POP_UP", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isLogin) {
            Intent intent = new Intent(this, TestingActivity.class);
            this.finishAffinity();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SpotlistActivity.class);
            this.finishAffinity();
            startActivity(intent);
        }
    }
}
