package com.example.test.accounts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.BottomNavigationViewHelper;
import com.example.test.MainActivity;
import com.example.test.MapsActivity;
import com.example.test.NewsActivity;
import com.example.test.R;
import com.example.test.SpotlistActivity;
import com.example.test.SqliteHelper;
import com.example.test.TestingActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.soundcloud.android.crop.Crop;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AccountPage extends AppCompatActivity {

    ImageView picture;
    SqliteHelper dbHelper;
    String currentUser;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        final Button logout = findViewById(R.id.logout_a);
        final TextView name = findViewById(R.id.name_a);
        final TextView email = findViewById(R.id.email_a);
//        final TextView liked = findViewById(R.id.liked_spots);
        picture = findViewById(R.id.profile_picture);
        dbHelper = new SqliteHelper(this);
        currentUser = dbHelper.getCurrent();
        name.setText(dbHelper.getUserName(currentUser));
        email.setText(currentUser);
        //TODO profile picture
        if (dbHelper.hasIcon(currentUser)) {
            picture.setImageBitmap(getRoundedCroppedBitmap(dbHelper.getIcon(currentUser)));
        }
//        if (userData.getPicture(userData.getCurrentUser()) != null) {
//            picture.setImageBitmap(getRoundedCroppedBitmap(userData.getPicture(userData.getCurrentUser())));
//        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutConfirm();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

//        liked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AccountPage.this, Liked.class);
//                startActivity(intent);
//            }
//        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case R.id.ic_news:
                        Intent intent1 = new Intent(AccountPage.this, NewsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ic_list:
                        if (dbHelper.isLogin()) {
                            Intent intent = new Intent(AccountPage.this, TestingActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        } else {
                            Intent intent = new Intent(AccountPage.this, SpotlistActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }
                        break;

                    case R.id.ic_Map:
                        Intent intent3 = new Intent(AccountPage.this, MapsActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ic_account:
//                        if (dbHelper.isLogin()) {
//                            Intent intent4 = new Intent(AccountPage.this, AccountPage.class);
//                            startActivity(intent4);
//                            startActivity(intent4);
//                        } else {
//                            Intent intent4 = new Intent(AccountPage.this, Login.class);
//                            startActivity(intent4);
//                            startActivity(intent4);
//                        }

                        break;
                }


                return false;
            }
        });
    }


    public void logoutConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure to log out?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dbHelper.logout();
                Intent intent = new Intent(AccountPage.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //canceled
            }
        });
        builder.show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Change Profile Image");
        String[] pictureDialogItems = {
                "Select photo from gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pickImage();
                                break;
//                            case 1:
////                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                                startActivityForResult(intent, RESULT_CAMARA);
//                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                                    startActivityForResult(takePictureIntent, RESULT_CAMARA);
//                                }
//                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    public void pickImage() {
        Crop.pickImage(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
//        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Date d = new Date();
        CharSequence s  = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped_" + s.toString()));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri pi = Crop.getOutput(result);
//            Bitmap newPic = loadBitmap(pi.toString());
            //TODO store profile picture
            boolean success = dbHelper.updateUserIcon(currentUser, pi.toString());
            if (success) {
                picture.setImageBitmap(getRoundedCroppedBitmap(dbHelper.getIcon(currentUser)));
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
//            userData.putPicture(userData.getCurrentUser(), newPic);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    private Bitmap getRoundedCroppedBitmap(Bitmap bitmap) {
        int widthLight = bitmap.getWidth();
        int heightLight = bitmap.getHeight();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paintColor = new Paint();
        paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

        RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

        canvas.drawRoundRect(rectF, widthLight / 2, heightLight / 2, paintColor);

        Paint paintImage = new Paint();
        paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, 0, 0, paintImage);

        return output;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_options, menu);
        MenuItem item = menu.findItem(R.id.create_admin);
        if (!dbHelper.isAdmin(currentUser)) {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.change_data:
                Intent intent = new Intent(AccountPage.this, ChangeData.class);
                startActivity(intent);
                return true;
            case R.id.create_admin:
                Intent i = new Intent(AccountPage.this, Register.class);
                i.putExtra("ADMIN", true);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            moveTaskToBack(true);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }





}
