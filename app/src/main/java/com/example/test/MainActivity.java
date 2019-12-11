package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;
import com.example.test.accounts.UserData;

public class MainActivity extends AppCompatActivity {
    private UserData userData = UserData.getInstance();

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private Object PackageManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void launchMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void launchAccount(View view) {
        Intent intent = new Intent(this, SpotlistActivity.class);
        startActivity(intent);
//        if (userData.isLogin()) {
//            Intent intent = new Intent(this, AccountPage.class);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//        }
    }
    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
    public void launchAr(View view) {
//        Intent intent = new Intent(this, artest.class);
//        startActivity(intent);
        String packname = "com.PlayNow.Playground";
        PackageManager packageManager = getPackageManager();
        if (checkPackInfo(packname)) {
            Intent intent = packageManager.getLaunchIntentForPackage(packname);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "You haven't install GeoPark AR",Toast.LENGTH_LONG ).show();
        }
    }

    public void launchGame(View view) {
        Intent intent = new Intent(this, game.class);
        startActivity(intent);
    }


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
