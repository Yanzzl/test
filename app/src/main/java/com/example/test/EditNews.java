package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class EditNews extends AppCompatActivity {

    String newsTitle;

    SqliteHelper dbHelper;
    Button update;
    EditText title;
    EditText description;
    ImageView picture;
    ImageView editPic;

    private static int PICK_IMAGE_SINGLE = 100;

    String selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        dbHelper = new SqliteHelper(this);
        newsTitle = getIntent().getStringExtra("TITLE");

        update = findViewById(R.id.update_en);
        title = findViewById(R.id.title_en);
        description = findViewById(R.id.description_en);
        picture = findViewById(R.id.imageView_en);
        editPic = findViewById(R.id.edit_en);

        fillContent();

        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPic();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_ = title.getText().toString();
                String description_ = description.getText().toString();
                if (title_.length() < 1 || description_.length() < 1 || selectedPhoto.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Please fill in required information", Toast.LENGTH_SHORT).show();
                } else {
                    if ((!title_.equals(newsTitle)) && dbHelper.isNews(title_)) {
                        Toast.makeText(getApplicationContext(), "Title already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        // remove old data
                        dbHelper.removeNews(newsTitle);
                        // add new data
                        dbHelper.addNews(title_, description_, selectedPhoto);
                        Toast.makeText(getApplicationContext(), "Update succeeded! " + selectedPhoto, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditNews.this, NewsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    public void pickPic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_SINGLE);
    }

    public void fillContent() {
        title.setText(newsTitle);
        Cursor data = dbHelper.getNews(newsTitle);
        if (data.getCount() == 0) {
            Toast.makeText(EditNews.this, "Load failed", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                description.setText(data.getString(2));
                picture.setImageURI(Uri.parse(data.getString(3)));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_SINGLE && resultCode == RESULT_OK
                    && null != result) {
                if (result.getData() != null) { //one selected
                    Uri mImageUri = result.getData();
                    makeFileFromUri(mImageUri);
                    picture.setImageURI(mImageUri);
                }

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


    }


    public void makeFileFromUri(Uri uri) {
        String name = getFileName(uri);
        Date d = new Date();
        CharSequence s = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        Uri destination = Uri.fromFile(new File(getCacheDir(), s.toString() + name));
        File file = new File(destination.getPath());
        Bitmap bitmap;
        FileOutputStream fos;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        selectedPhoto = destination.toString();
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


}
