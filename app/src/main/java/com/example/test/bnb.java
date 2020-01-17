package com.example.test;

import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bnb extends AppCompatActivity {
    private static final String TAG = "bnb";

    private SectionsPageAdapter mSectionsPageAdapter;
    SqliteHelper dbHelper;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnb);
        dbHelper = new SqliteHelper(this);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//        setupViewPager(mViewPager);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);


//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_assignment);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_autorenew);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_attach_file);

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
                        Intent intent1 = new Intent(bnb.this, MapsActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_list:
                        Intent intent2 = new Intent(bnb.this, SpotlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Map:
                        Intent intent3 = new Intent(bnb.this, NewsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_account:
                        if (dbHelper.isLogin()) {
                            Intent intent4 = new Intent(bnb.this, AccountPage.class);
                            startActivity(intent4);
                            startActivity(intent4);
                        } else {
                            Intent intent4 = new Intent(bnb.this, Login.class);
                            startActivity(intent4);
                            startActivity(intent4);
                        }

                        break;
                }


                return false;
            }
        });

    }

//    private void setupViewPager(ViewPager viewPager) {
//        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
////        adapter.addFragment(new Tab1Fragment());
////        adapter.addFragment(new Tab2Fragment());
////        adapter.addFragment(new Tab3Fragment());
//        viewPager.setAdapter(adapter);
//    }

}
