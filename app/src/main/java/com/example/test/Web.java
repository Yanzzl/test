package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView web = findViewById(R.id.web_game);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/game_5/index.html");
    }
}
