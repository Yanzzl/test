package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ElevatorB3 extends AppCompatActivity {
    Button btnUp;
    TextView txtGame;

    String spotTitle;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_b3);

        btnUp = (Button) findViewById(R.id.btnUpF3);
        txtGame = (TextView) findViewById(R.id.txtGame);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB3.this, ElevatorB2.class);
                intent.putExtra("TITLE", spotTitle);
                intent.putExtra("IS_LOGIN", isLogin);
                startActivity(intent);
            }
        });

        txtGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElevatorB3.this, game.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isLogin) {
            Intent intent = new Intent(this, TestingActivity.class);
//            this.finishAffinity();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SpotlistActivity.class);
//            this.finishAffinity();
            startActivity(intent);
        }
    }
}
