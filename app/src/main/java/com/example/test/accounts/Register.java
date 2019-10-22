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

public class Register extends AppCompatActivity {

    private UserData userData = UserData.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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
                        if (userData.isUser(email)) {
                            Toast.makeText(getApplicationContext(), "Email already used", Toast.LENGTH_SHORT).show();
                        } else {
                        userData.putName(email, nick);
                        userData.putPassword(email, password);
                        userData.login(email);
                        Intent intent = new Intent(Register.this, AccountPage.class);
                        startActivity(intent);
                    }
                }
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.logout();
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
