package com.example.test.accounts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.BottomNavigationViewHelper;
import com.example.test.MainActivity;
import com.example.test.MapsActivity;
import com.example.test.NewsActivity;
import com.example.test.R;
import com.example.test.SpotlistActivity;
import com.example.test.SqliteHelper;
import com.example.test.bnb;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private static View view;
    SqliteHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final ImageView info = findViewById(R.id.info_l);
        final Button login = findViewById(R.id.login_l);
        final TextView register = findViewById(R.id.register_l);
        final Button guest = findViewById(R.id.guest_l);
        final EditText user;
        user = findViewById(R.id.username_l);
        final EditText pass;
        pass = findViewById(R.id.password_l);

        dbHelper = new SqliteHelper(this);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString();
                String password = pass.getText().toString();
                if (dbHelper.isUser(email) && dbHelper.correctPassword(email, password)) {
                    dbHelper.login(email);
                    Intent intent = new Intent(Login.this, AccountPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPop();
            }
        });



    }

    public void infoPop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextView title = new TextView(this);
        builder.setTitle("For user test, a default account is provided:");
        String text = "Email: admin@gmail.com\nPassword: 123456";
        title.setText(text);
        title.setPadding(70, 20, 0, 0);
        title.setTextSize(17);
        title.setTextColor(this.getResources().getColor(R.color.black));
        builder.setView(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //close window
            }
        });
        builder.show();
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, NewsActivity.class);
//        this.finishAffinity();
//        startActivity(intent);
//    }




}
