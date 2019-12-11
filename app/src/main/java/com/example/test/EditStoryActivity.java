package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditStoryActivity extends AppCompatActivity {
    SqliteHelper dbHelper;
    Button btnAdd, btnView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        dbHelper = new SqliteHelper(this);
        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditStoryActivity.this, SpotlistActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0){
                    AddData(newEntry);
                    editText.setText("");
                } else {
                    Toast.makeText(EditStoryActivity.this, "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void AddData(String newEntry){
        boolean flag = dbHelper.addData(newEntry);

        if(flag){
            Toast.makeText(EditStoryActivity.this, "Insert data sussess.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(EditStoryActivity.this, "Insert data fail.", Toast.LENGTH_LONG).show();
        }
    }
}
