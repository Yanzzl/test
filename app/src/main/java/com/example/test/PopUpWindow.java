package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Liked;
import com.example.test.accounts.UserData;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class PopUpWindow extends AppCompatActivity {

    //    private UserData userData = UserData.getInstance();
    SqliteHelper dbHelper;
    String title;
    TextView titleView;
    TextView descriptionView;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
        dbHelper = new SqliteHelper(this);
        title = getIntent().getStringExtra("TITLE");

        titleView = findViewById(R.id.title_p);
        descriptionView = findViewById(R.id.description_p);
        viewPager = (ViewPager) findViewById(R.id.viewPager_p);


        titleView.setText(title);
        fillContent();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .75));


        ImageView star = findViewById(R.id.star);
        if (dbHelper.isLogin()) {
            String currentUser = dbHelper.getCurrent();
            if (dbHelper.isFavoriteSpots(currentUser, title)) {
                star.setImageResource(R.drawable.ic_star_yellow);
            } else {
                star.setImageResource(R.drawable.ic_star_gray);
            }
        }
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.isLogin()) {
                    String currentUser = dbHelper.getCurrent();
                    if (dbHelper.isFavoriteSpots(currentUser, title)) {
                        dbHelper.removeFavorite(currentUser, title);
                        star.setImageResource(R.drawable.ic_star_gray);
                    } else {
                        star.setImageResource(R.drawable.ic_star_yellow);
                        dbHelper.setFavorite(currentUser, title);
                    }
                } else {
                    Toast.makeText(PopUpWindow.this, "Please login first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button game = findViewById(R.id.info_games);
        Button ar = findViewById(R.id.info_ar);

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (title.equals("Boortoren")) {
                    Intent intent = new Intent(PopUpWindow.this, game.class);
                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(PopUpWindow.this, "This function is not implemented yet. For game example," +
//                            " please try out the game function of Boortoren", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String packname = "com.PlayNow.Playground";
//                PackageManager packageManager = getPackageManager();
//                if (checkPackInfo(packname)) {
//                    Intent intent = packageManager.getLaunchIntentForPackage(packname);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(PopUpWindow.this, "You haven't install GeoPark AR", Toast.LENGTH_LONG).show();
//                }


//                if (title.equals("Boortoren")) {
                    Intent intent = new Intent(PopUpWindow.this, ar12_9.class);
                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(PopUpWindow.this, "This function is not implemented yet. For AR example," +
//                            " please try out the AR function of Boortoren", Toast.LENGTH_SHORT).show();
//                }
            }

            private boolean checkPackInfo(String packname) {
                PackageInfo packageInfo = null;
                try {
                    packageInfo = getPackageManager().getPackageInfo(packname, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                return packageInfo != null;
            }
        });
    }


    public void fillContent(){
        Cursor data = dbHelper.getSpot(title);
        ArrayList<Uri> pics = dbHelper.getPictures(title);
        if (data.getCount() == 0) {
            Toast.makeText(PopUpWindow.this, "Load failed", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                descriptionView.setText(data.getString(4));
            }
        }
        if (pics.size() < 1) {
            Toast.makeText(PopUpWindow.this, "No picture", Toast.LENGTH_SHORT).show();
        } else {
//            imageView.setImageURI(pics.get(0));
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(pics, this);
            viewPager.setAdapter(viewPagerAdapter);
        }
    }


}
