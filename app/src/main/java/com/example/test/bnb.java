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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class bnb extends AppCompatActivity {
    private static final String TAG = "bnb";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnb);

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
                    case R.id.ic_arrow:

                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(bnb.this, MapsActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(bnb.this, SpotlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(bnb.this, ActivityThree.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(bnb.this, ActivityFour.class);
                        startActivity(intent4);
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
