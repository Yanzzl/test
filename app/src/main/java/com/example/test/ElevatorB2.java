package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ElevatorB2 extends AppCompatActivity {
    Button btnUp;
    Button btnDown;
    TextView txtAR;

    String spotTitle;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_b2);

        btnUp = (Button) findViewById(R.id.btnUpF2);
        btnDown = (Button) findViewById(R.id.btnDownF2);
        txtAR = (TextView) findViewById(R.id.txtAR);

        spotTitle = getIntent().getStringExtra("TITLE");
        isLogin = getIntent().getBooleanExtra("IS_LOGIN", false);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB2.this, ElevatorB1.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("IS_LOGIN", isLogin);
                startActivity(intent);
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB2.this, ElevatorB3.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("IS_LOGIN", isLogin);
                startActivity(intent);
            }
        });

        txtAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB2.this, ar12_9.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("POP_UP", true);
                startActivity(intent);
            }
        });
    }
}
