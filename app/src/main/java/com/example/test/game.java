package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Liked;
import com.example.test.accounts.UserData;

public class game extends AppCompatActivity {

    private UserData userData = UserData.getInstance();

    ImageView g1;
    ImageView g2;
    ImageView g3;
    ImageView g4;
    ImageView g5;
    ConstraintLayout layout;
    AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        layout = findViewById(R.id.game_layout);
        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();


        g1 = findViewById(R.id.g1);
        if (userData.getGameState(1)) {
            g1.setImageResource(R.drawable.g1);
        } else {
            g1.setImageResource(R.drawable.g1_locked);
        }

        g2 = findViewById(R.id.g2);
        if (userData.getGameState(2)) {
            g2.setImageResource(R.drawable.g2);
        } else {
            g2.setImageResource(R.drawable.g2_locked);
        }

        g3 = findViewById(R.id.g3);
        if (userData.getGameState(3)) {
            g3.setImageResource(R.drawable.g3);
        } else {
            g3.setImageResource(R.drawable.g3_locked);
        }

        g4 = findViewById(R.id.g4);
        if (userData.getGameState(4)) {
            g4.setImageResource(R.drawable.g4);
        } else {
            g4.setImageResource(R.drawable.g4_locked);
        }

        g5 = findViewById(R.id.g5);
        if (userData.getGameState(5)) {
            g5.setImageResource(R.drawable.g5);
        } else {
            g5.setImageResource(R.drawable.g5_locked);
        }


        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.getGameState(1)) {
                    userData.unlockGame(2);
                    Intent intent = new Intent(game.this, Web.class);
                    intent.putExtra("GAME", 1);
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
                    intent.putExtra("GAME", 2);
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
                    intent.putExtra("GAME", 3);
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
                    intent.putExtra("GAME", 4);
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
                    intent.putExtra("GAME", 5);
                    startActivity(intent);
                } else {

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finishAffinity();
        startActivity(intent);
    }

}
