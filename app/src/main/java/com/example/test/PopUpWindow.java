package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.accounts.UserData;
import com.soundcloud.android.crop.Crop;

public class PopUpWindow extends AppCompatActivity {

    private UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.75));

        ImageView star = findViewById(R.id.star);
        if (userData.isLogin()) {
            String currentUser = userData.getCurrentUser();
            if (userData.isStared(currentUser)) {
                star.setImageResource(R.drawable.ic_star_yellow);
            } else {
                star.setImageResource(R.drawable.ic_star_gray);
            }
        }
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userData.isLogin()) {
                    String currentUser = userData.getCurrentUser();
                    if (userData.isStared(currentUser)) {
                        star.setImageResource(R.drawable.ic_star_gray);
                        userData.unStar(currentUser);
                    } else {
                        star.setImageResource(R.drawable.ic_star_yellow);
                        userData.star(currentUser);
                    }
                } else {
                    Toast.makeText(PopUpWindow.this, "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
