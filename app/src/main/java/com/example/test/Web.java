package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class Web extends AppCompatActivity {

    int game;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        game = getIntent().getIntExtra("GAME", 0);

        WebView web = findViewById(R.id.web_game);
        web.getSettings().setJavaScriptEnabled(true);

        switch (game) {
            case 0:
                System.out.println("Not received");
            case 1:
                web.loadUrl("file:///android_asset/game_1/index.html");
                break;
            case 2:
                web.loadUrl("file:///android_asset/game_2/index.html");
                break;
            case 3:
                web.loadUrl("file:///android_asset/game_3/index.html");
                break;
            case 4:
                web.loadUrl("file:///android_asset/game_4/index.html");
                break;
            case 5:
                web.loadUrl("file:///android_asset/game_5/index.html");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, game.class);
        this.finishAffinity();
        startActivity(intent);
    }
}
