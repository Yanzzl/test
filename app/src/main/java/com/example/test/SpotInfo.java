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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpotInfo extends AppCompatActivity implements ImageListAdapter.ItemClickListener {
    String spotTitle;

    SqliteHelper dbHelper;
    Button update;
    EditText title;
    EditText latitude;
    EditText longitude;
    EditText description;
    ImageView picture;
    ImageView game;
    ImageView ar;
    TextView pic_id;
    RecyclerView recyclerView;
    TextView game_id;
    TextView ar_id;

    private static int RESULT_LOAD_AR = 200;
    private static int RESULT_LOAD_GAME = 300;
    private static int PICK_IMAGE_MULTIPLE = 400;

    List<String> selectedPhoto = new ArrayList<>();

    ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_info);
        dbHelper = new SqliteHelper(this);
        spotTitle = getIntent().getStringExtra("TITLE");

        update = findViewById(R.id.update_si);
        title = findViewById(R.id.title_si);
        latitude = findViewById(R.id.latitude_si);
        longitude = findViewById(R.id.longitude_si);
        description = findViewById(R.id.description_si);
        picture = findViewById(R.id.picture_si);
        game = findViewById(R.id.game_si);
        ar = findViewById(R.id.ar_si);
        pic_id = findViewById(R.id.picture_sid);
        recyclerView = findViewById(R.id.picList_si);
        game_id = findViewById(R.id.game_sid);
        ar_id = findViewById(R.id.ar_sid);

        fillContent();

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMultipleImages();
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGame();
            }
        });
        ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickAr();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_ = title.getText().toString();
                String latitude_ = latitude.getText().toString();
                String longitude_ = longitude.getText().toString();
                String description_ = description.getText().toString();
                if (title_.length() < 1 || latitude_.length() < 1 || longitude_.length() < 1 || description_.length() < 1 || selectedPhoto.size() < 1) {
                    Toast.makeText(getApplicationContext(), "Please fill in required information", Toast.LENGTH_SHORT).show();
                } else {
                    if ((!title_.equals(spotTitle)) && dbHelper.isGeopoint(title_)) {
                        Toast.makeText(getApplicationContext(), "There already exists a spot with this title", Toast.LENGTH_SHORT).show();
                    } else {
                        double la = isUsable(latitude_);
                        double lo = isUsable(longitude_);
                        if (la < 0 || lo < 0) {
                            Toast.makeText(getApplicationContext(), "Please fill in correct latitude and longitude", Toast.LENGTH_SHORT).show();
                        } else {
                            // remove old data
                            clearData();
                            // add new data
                            dbHelper.addGeoPoint(title_, description_, la, lo);
                            for (String pic : selectedPhoto) {
                                dbHelper.addGeoPicture(title_, pic);
                            }
                            Toast.makeText(getApplicationContext(), "Update succeeded! " + selectedPhoto, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SpotInfo.this, TestingActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }


    public void clearData() {
        dbHelper.removeGeoPoint(spotTitle);
        dbHelper.removeGeoPics(spotTitle);
    }

    public void pickMultipleImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    public static double isUsable(String str) {
        try {
            double result = Double.parseDouble(str);
            return result;
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    //TODO
    public void pickGame() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Pick a file"), RESULT_LOAD_GAME);

    }


    public void pickAr() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Pick a file"), RESULT_LOAD_AR);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != result) {
                if (result.getData() != null) { //one selected
                    Uri mImageUri = result.getData();
                    makeFileFromUri(mImageUri);
                    setAdapter();
                } else { // multiple selected
                    if (result.getClipData() != null) {
                        ClipData mClipData = result.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            makeFileFromUri(uri);
                            setAdapter();
                        }
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


        //AR and game part
        if (requestCode == RESULT_LOAD_AR && resultCode == RESULT_OK && null != result) {
            Uri selectedFile = result.getData();
            String name = getFileName(selectedFile);
            ar_id.setText(name);

        }
        if (requestCode == RESULT_LOAD_GAME && resultCode == RESULT_OK && null != result) {
            Uri selectedFile = result.getData();
            String name = getFileName(selectedFile);
            game_id.setText(name);

        }

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
        selectedPhoto.add(destination.toString());
    }

    public void setAdapter() {
        adapter = new ImageListAdapter(this, selectedPhoto);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        pic_id.setText("Selected photo count: " + this.selectedPhoto.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        selectedPhoto.remove(position);
        setAdapter();
    }

    public void fillContent() {
        title.setText(spotTitle);

        ArrayList<Uri> pics = dbHelper.getPictures(spotTitle);
        for (Uri pic : pics) {
            this.selectedPhoto.add(pic.toString());
        }
        setAdapter();

        Cursor data = dbHelper.getSpot(spotTitle);
        if (data.getCount() == 0) {
            Toast.makeText(SpotInfo.this, "Load failed", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                String la = Double.toString(data.getDouble(2));
                String lo = Double.toString(data.getDouble(3));
                latitude.setText(la);
                longitude.setText(lo);
                description.setText(data.getString(4));
            }
        }
    }
}
