package com.example.test.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.test.MapsActivity;
import com.example.test.PopUpWindow;
import com.example.test.R;

public class Liked extends AppCompatActivity {

    UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        TextView liked = findViewById(R.id.boortoren);

        if (userData.isLogin()) {
            String current = userData.getCurrentUser();
            if (userData.isStared(current)) {
                liked.setVisibility(View.VISIBLE);
            }
        }

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Liked.this, MapsActivity.class);
                intent.putExtra("POP_UP_BOORTOREN", true);
                startActivity(intent);
            }
        });
    }
}
