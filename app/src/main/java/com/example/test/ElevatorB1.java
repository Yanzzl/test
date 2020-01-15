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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_b1);

        btnDown = (Button) findViewById(R.id.btnDown);;
        txtMap = (TextView) findViewById(R.id.txtMap);

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB1.this, ElevatorB2.class);
                startActivity(intent);
            }
        });

        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB1.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
