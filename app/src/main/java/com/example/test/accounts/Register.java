package com.example.test.accounts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.SpotlistActivity;
import com.example.test.SqliteHelper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    SqliteHelper dbHelper;
    boolean admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        admin = getIntent().getBooleanExtra("ADMIN", false);

        dbHelper = new SqliteHelper(this);

        final Button register = findViewById(R.id.register_r);
        final TextView guest = findViewById(R.id.guest_r);
        final EditText user;
        user = findViewById(R.id.username_r);
        final EditText pass;
        pass = findViewById(R.id.password_r);
        final EditText name;
        name = findViewById(R.id.name_r);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString();
                String password = pass.getText().toString();
                String nick = name.getText().toString();
                if (email.length() < 1 || password.length() < 1 || nick.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the information", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.isUser(email)) {
                        Toast.makeText(getApplicationContext(), "Email already used", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean succeed;
                        if (admin) {
                            succeed = dbHelper.addAccount(email, nick, password, 1);
                        } else {
                            succeed = dbHelper.addAccount(email, nick, password, 0);
                        }
                        if (succeed) {
                            dbHelper.logout();
                            dbHelper.login(email);
                            Toast.makeText(getApplicationContext(), "Register succeeded!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, AccountPage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }




}
