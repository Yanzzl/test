package com.example.test.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.MainActivity;
import com.example.test.R;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private static View view;

    private UserData userData = UserData.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final Button login = findViewById(R.id.login_l);
        final TextView register = findViewById(R.id.register_l);
        final TextView guest = findViewById(R.id.guest_l);
        final EditText user;
        user = findViewById(R.id.username_l);
        final EditText pass;
        pass = findViewById(R.id.password_l);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString();
                String password = pass.getText().toString();
                if (userData.isUser(email) && userData.getPassword(email).equals(password)) {
                    userData.login(email);
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
                userData.logout();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}
