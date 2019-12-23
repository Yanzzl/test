package com.example.test.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.SqliteHelper;

import java.util.ArrayList;

public class ChangeData extends AppCompatActivity {


    String currentUser;
    SqliteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);

        dbHelper = new SqliteHelper(this);
        currentUser = dbHelper.getCurrent();

        final TextView name = findViewById(R.id.change_name);
        final TextView email = findViewById(R.id.change_email);
        email.setVisibility(View.INVISIBLE);
        final TextView password = findViewById(R.id.change_password);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });

//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeEmail();
//            }
//        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOldPass();
            }
        });

    }

    public void changeName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.change_name, null));
        builder.setTitle("Enter your new name");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //canceled
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked OK, so save the selectedItems results somewhere
                // or return them to the component that opened the dialog
                EditText edit =  ((AlertDialog) dialog).findViewById(R.id.name_c);
                String newName = edit.getText().toString();
                if (newName.length()<1) {
                    Toast.makeText(getApplicationContext(), "Please fill in new name", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.updateUserName(currentUser, newName);
                    Intent intent = new Intent(ChangeData.this, AccountPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void changeEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.change_name, null));
        builder.setTitle("Enter your new Email");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //canceled
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit =  ((AlertDialog) dialog).findViewById(R.id.name_c);
                String newEmail = edit.getText().toString();
                if (newEmail.length()<1) {
                    Toast.makeText(getApplicationContext(), "Please fill in new email", Toast.LENGTH_SHORT).show();
                } else {
                    if (!dbHelper.isUser(newEmail)) {
                        dbHelper.updateUserEmail(currentUser, newEmail);
                        Intent intent = new Intent(ChangeData.this, AccountPage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "This email is already used", Toast.LENGTH_SHORT).show();
                    }
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
            }
        });
    }
//
//
    public void enterOldPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.change_name, null));
        builder.setTitle("Enter your old password");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //canceled
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked OK, so save the selectedItems results somewhere
                // or return them to the component that opened the dialog
                EditText edit =  ((AlertDialog) dialog).findViewById(R.id.name_c);
                String oldPass = edit.getText().toString();
                if (!dbHelper.correctPassword(currentUser, oldPass)) {
                    Toast.makeText(getApplicationContext(), "Please fill in correct password", Toast.LENGTH_SHORT).show();
                } else {
                    enterNewPass();
                }
            }
        });
    }
//
//
    public void enterNewPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.change_name, null));
        builder.setTitle("Enter your new password");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //canceled
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User clicked OK, so save the selectedItems results somewhere
                // or return them to the component that opened the dialog
                EditText edit =  ((AlertDialog) dialog).findViewById(R.id.name_c);
                String newPass = edit.getText().toString();
                if (newPass.length()<1) {
                    Toast.makeText(getApplicationContext(), "Please fill in new password", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.updateUserPass(currentUser, newPass);
                    Intent intent = new Intent(ChangeData.this, AccountPage.class);
                    startActivity(intent);
                }
            }
        });
    }


}
