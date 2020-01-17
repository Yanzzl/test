package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    SqliteHelper dbHelper;
    boolean isAdmin = false;
    private SectionsPageAdapter mSectionsPageAdapter;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        Button add = findViewById(R.id.addNews_news);
        ListView listView = (ListView) findViewById(R.id.list_news);
        dbHelper = new SqliteHelper(this);
        if (dbHelper.isLogin()) {
            isAdmin = dbHelper.isAdmin(dbHelper.getCurrent());
        }
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getNews();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, MakeNews.class);
                startActivity(intent);
            }
        });
        if (data.getCount() == 0) {
            Toast.makeText(NewsActivity.this, "No data", Toast.LENGTH_SHORT).show();

        }
        while (data.moveToNext()) {
            theList.add(data.getString(1));
        }
        if (isAdmin) {
            add.setVisibility(View.VISIBLE);
        }
        //instantiate custom adapter
        NewsCardAdapter adapter = new NewsCardAdapter(theList, this, isAdmin);
        //handle listview and assign adapter
        listView.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case R.id.ic_news:
//                        Intent intent1 = new Intent(NewsActivity.this, MapsActivity.class);
//                        startActivity(intent1);
//                        break;

                    case R.id.ic_list:
                        if (dbHelper.isLogin()) {
                            Intent intent = new Intent(NewsActivity.this, TestingActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(NewsActivity.this, SpotlistActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case R.id.ic_Map:
                        Intent intent3 = new Intent(NewsActivity.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_account:
                        if (dbHelper.isLogin()) {
                            Intent intent4 = new Intent(NewsActivity.this, AccountPage.class);
                            startActivity(intent4);

                        } else {
                            Intent intent4 = new Intent(NewsActivity.this, Login.class);
                            startActivity(intent4);

                        }

                        break;
                }


                return false;
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, MainActivity.class);
//        this.finishAffinity();
//        startActivity(intent);
//    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            moveTaskToBack(true);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

}
