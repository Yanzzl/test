package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Liked;
import com.example.test.accounts.UserData;

public class game extends AppCompatActivity {

    private UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageView g1 = findViewById(R.id.g1);
        if (userData.getGameState(1)) {
            g1.setImageResource(R.drawable.g1);
        } else {
            g1.setImageResource(R.drawable.g1_locked);
        }

        ImageView g2 = findViewById(R.id.g2);
        if (userData.getGameState(2)) {
            g1.setImageResource(R.drawable.g2);
        } else {
            g1.setImageResource(R.drawable.g2_locked);
        }

        ImageView g3 = findViewById(R.id.g3);
        if (userData.getGameState(3)) {
            g1.setImageResource(R.drawable.g3);
        } else {
            g1.setImageResource(R.drawable.g3_locked);
        }

        ImageView g4 = findViewById(R.id.g4);
        if (userData.getGameState(4)) {
            g1.setImageResource(R.drawable.g4);
        } else {
            g1.setImageResource(R.drawable.g4_locked);
        }

        ImageView g5 = findViewById(R.id.g5);
        if (userData.getGameState(5)) {
            g1.setImageResource(R.drawable.g5);
        } else {
            g1.setImageResource(R.drawable.g5_locked);
        }


        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(1)) {
                    userData.unlockGame(2);
                    Intent intent = new Intent(game.this, Web.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(2)) {
                    userData.unlockGame(3);
                    Intent intent = new Intent(game.this, Web.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        g3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(3)) {
                    userData.unlockGame(4);
                    Intent intent = new Intent(game.this, Web.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        g4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(4)) {
                    userData.unlockGame(5);
                    Intent intent = new Intent(game.this, Web.class);
                    startActivity(intent);
                } else {

                }
            }
        });

        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(5)) {
                    Intent intent = new Intent(game.this, Web.class);
                    startActivity(intent);
                } else {

                }
            }
        });



    }

}
