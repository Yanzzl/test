package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
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
import java.util.Date;

public class MakeNews extends AppCompatActivity {
    SqliteHelper dbHelper;
    Button upload;
    EditText title;
    EditText description;
    ImageView picture;
    TextView pic_id;

    private static int PICK_IMAGE_SINGLE = 100;

    String selectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_news);

        dbHelper = new SqliteHelper(this);
        upload = findViewById(R.id.upload_mn);
        title = findViewById(R.id.title_mn);
        description = findViewById(R.id.description_mn);
        picture = findViewById(R.id.picture_mn);
        pic_id = findViewById(R.id.picture_mnid);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPic();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_ = title.getText().toString();
                String description_ = description.getText().toString();
                if (title_.length() < 1 || description_.length() < 1 || selectedPhoto.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Please fill in required information", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.isNews(title_)) {
                        Toast.makeText(getApplicationContext(), "Title already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.addNews(title_, description_, selectedPhoto);
                        Toast.makeText(getApplicationContext(), "Upload succeeded! ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MakeNews.this, NewsActivity.class);
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
                    String name = getFileName(mImageUri);
                    pic_id.setText(name);
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
