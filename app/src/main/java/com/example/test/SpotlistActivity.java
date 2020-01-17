package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SpotlistActivity extends AppCompatActivity {
    SqliteHelper dbHelper;
    boolean isAdmin = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotlist);

        dbHelper = new SqliteHelper(this);

        ListView listView = (ListView) findViewById(R.id.spotList_sl);
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getGeoPoints();
        while (data.moveToNext()) {
            theList.add(data.getString(1));
        }


//        Button add = findViewById(R.id.addSpot_s);
//        add.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SpotlistActivity.this, Upload.class);
//                startActivity(intent);
//            }
//        });
//        if (dbHelper.isLogin()) {
//            isAdmin = dbHelper.isAdmin(dbHelper.getCurrent());
//        }
//        if (isAdmin) {
//            add.setVisibility(View.VISIBLE);
//            //instantiate custom adapter
//            MyCustomAdapter adapter = new MyCustomAdapter(theList, this);
//            //handle listview and assign adapter
//            listView.setAdapter(adapter);
//
//        } else {
        ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.list_item_normal, theList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // original working spotlist
//                    Intent intent = new Intent(SpotlistActivity.this, MapsActivity.class);
//                    intent.putExtra("TITLE", selectedItem);
//                    intent.putExtra("POP_UP", true);
//                    startActivity(intent);
                Intent intent = new Intent(SpotlistActivity.this, ElevatorB1.class);
                intent.putExtra("TITLE", selectedItem);
                intent.putExtra("IS_LOGIN", false);
                startActivity(intent);

                // added
//                    Intent intent = new Intent(SpotlistActivity.this, ElevatorActivity.class);
//                    intent.putExtra("Key", "Value");
//                    startActivity(intent);
            }
        });
//        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case R.id.ic_news:
                        Intent intent1 = new Intent(SpotlistActivity.this, NewsActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_list:
//                        Intent intent2 = new Intent(SpotlistActivity.this, SpotlistActivity.class);
//                        startActivity(intent2);
//                        break;

                    case R.id.ic_Map:
                        Intent intent3 = new Intent(SpotlistActivity.this, MapsActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_account:
                        if (dbHelper.isLogin()) {
                            Intent intent4 = new Intent(SpotlistActivity.this, AccountPage.class);
                            startActivity(intent4);

                        } else {
                            Intent intent4 = new Intent(SpotlistActivity.this, Login.class);
                            startActivity(intent4);

                        }

                        break;
                }


                return false;
            }
        });

    }

//
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