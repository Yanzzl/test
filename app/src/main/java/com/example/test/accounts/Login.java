package com.example.test.accounts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


        final ImageView info = findViewById(R.id.info_l);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finishAffinity();
        startActivity(intent);
    }
}
