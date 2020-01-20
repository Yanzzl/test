package com.example.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DB_H.db";
    public static final String TABLE1 = "spot";
    public static final String TABLE = "spot";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";

    public static final String DB_TEST = "DB_TEST52.db";
    public static final String SPOT = "geopoint";
    public static final String title = "TITLE";
    public static final String description = "DESCRIPTION";
    public static final String latitude = "LATITUDE";
    public static final String longitude = "LONGITUDE";

    public static final String PICTURE = "picture";
    public static final String picValue = "VALUE";

    public static final String ACCOUNT = "account";
    public static final String email = "EMAIL";
    public static final String name = "NAME";
    public static final String password = "PASSWORD";
    public static final String isAdmin = "ISADMIN";
    public static final String userIcon = "ICON";
    public static final String hasIcon = "HASICON";

    public static final String NEWS = "news";
    public static final String content = "CONTENT";
    public static final String newsPic = "NEWSPIC";


    public static final String STAR = "star";

    public static final String LOGIN = "login";

    private Context context;


//    public static final byte[] defaultIcon = "0".getBytes();


    public SqliteHelper(Context context) {
        super(context, DB_TEST, null, 1);
        // Application Context
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + SPOT + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, LATITUDE REAL, LONGITUDE REAL, DESCRIPTION TEXT)";
        String createTable2 = "CREATE TABLE " + ACCOUNT + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, NAME TEXT, PASSWORD TEXT, ISADMIN INTEGER, ICON TEXT, HASICON INTEGER)";
        String createTable3 = "CREATE TABLE " + PICTURE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, VALUE TEXT)";
        String createTable4 = "CREATE TABLE " + STAR + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, TITLE TEXT)";
        String createTable5 = "CREATE TABLE " + LOGIN + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT)";
        String createTable6 = "CREATE TABLE " + NEWS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CONTENT TEXT, NEWSPIC TEXT)";

        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
        db.execSQL(createTable6);

        addDefaultUser(db);
        addDefaultSpots(db);
        addDefaultPics(db);
        addDefaultNews(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SPOT);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + PICTURE);
        db.execSQL("DROP TABLE IF EXISTS " + STAR);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + NEWS);

        onCreate(db);

    }

    public boolean addData(String item1) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);

        long result = db.insert(TABLE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE, null);
        return data;
    }


    public boolean addGeoPoint(String title_, String description_, double latitude_, double longitude_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(title, title_);
        contentValues.put(description, description_);
        contentValues.put(latitude, latitude_);
        contentValues.put(longitude, longitude_);
//        contentValues.put(picture, picture_);

        long result = db.insert(SPOT, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean isGeopoint(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SPOT + " WHERE TITLE = '" + title_ + "'", null);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isNews(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + NEWS + " WHERE TITLE = '" + title_ + "'", null);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addGeoPicture(String title_, String picValue_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title, title_);
        contentValues.put(picValue, picValue_);

        long result = db.insert(PICTURE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean addAccount(String email_, String name_, String password_, int isAdmin_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(email, email_);
        contentValues.put(name, name_);
        contentValues.put(password, password_);
        contentValues.put(isAdmin, isAdmin_);
        contentValues.put(hasIcon, 0);


        long result = db.insert(ACCOUNT, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addNews(String title_, String description_, String newsPic_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(title, title_);
        contentValues.put(content, description_);
        contentValues.put(newsPic, newsPic_);

        long result = db.insert(NEWS, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean addDefaultUser(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(email, "admin@gmail.com");
        contentValues.put(name, "admin");
        contentValues.put(password, "123456");
        contentValues.put(isAdmin, 1);
        contentValues.put(hasIcon, 0);


        long result = db.insert(ACCOUNT, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDefaultSpots(SQLiteDatabase db) {
        String descriptionBoortoren = "Boortoren is a drilling tower which was used back in around 1919 to drill holes in the ground and receive salt.\n\n" +
                "As time progressed, mobile drilling stations were created and this was replaced with smaller, cheaper and less striking salt houses above the caverns.";
        String description1 = "Het museum toont zout in al zijn aspecten: geologie, traditionele en moderne winmethode, functies, taal en volksgeloof en milieuzaken. Verder een zeewateraquarium en een grote collectie zoutvaatjes. Het zoutmuseum Delden bevindt zich vlakbij de plaats waar in 1886 zout in de Nederlandse bodem werd ontdekt. Zout is een kostbare bodemschat. In het zoutmuseum ontdekt u wat men vroeger met zout deed en waarom het nu nog steeds belangrijk voor ons is.\n" +
                "De kern van het museum is de basisexpositie op de eerste verdieping. Hier worden verschillende aspecten van het zout belicht. Van het ontstaan van de zoutlagen, de ontdekking van zout in de Twentse bodem, de winning van zout, de chemie, het milieu tot en met de symboliek en de cultuur van het zout.\n" +
                "Langestraat 30, 7491AG Delden, 074-3764546\n";
        String description2 = "Het oudste natuurmuseum van Nederland met een fraaie collectie over flora en fauna, alles over planten en dieren uit de oertijd en over de aarde van binnen en buiten. Verder is er een heus rariteitenkabinet, eentje die sedert de bouw van het museum niet is meer veranderd. Natuurlandschappen en diorama''s en veel over de natuur van dinosauri?rs tot kikkers. Verschillende zalen met o.a. vogels en weersateliet en een echt werkende seismograaf, een gesteentezaal en een vogel-/zoogdierenzaal met een mooi overzicht van de Nederlandse vogel- en zoogdierbevolking. In de landschappenzaal is de geschiedenis en toekomst van het Twentsche landschap het onderwerp. Met prachtige foto''s, diorama''s en kaarten. \n" +
                "Ook erg geschikt voor kinderen: tussen de slagtanden van een mammoet staan? Snijden met een vuursteen zoals onze voorouders? De schubben van een fossiele vis aanraken? Dat kan allemaal in de paleontologiezaal van het museum. Daar vind je ook fossielen, versteende afdrukken van planten en dieren.\n";
        String description3 = "To be added ";
        String description4 = "To be added ";
        String description5 = "To be added ";
        String description6 = "Permanente expositie van o.a. planten, gesteenten en mineralen, en fossielen in hoofdzaak afkomstig uit Twente en omgeving. Verder, diorama''s met Nederlandse zoogdieren en vogels. Fossiele en recente walvissen. Nederlandse vissen, reptielen en amfibie?n, modellen en levend. ''s Zomers levend bijenvolkje. Sterrenwacht, beperkt geopend.\n";
        String description7 = "Permanente expositie van archeologie, fossielen, gesteenten en prehistorie in het Raadhuis, Raadhuisplein 1, 7581 AG Losser, 053-5377462\n";
        String description8 = "Het Dr. W. Staringmonument met zandsteengroeve aan de Dr. Staringstraat. \n" +
                "In 1965 waren er plannen om op de Losserse es een woonwijk te bouwen. Op initiatief van W.F. Anderson, inwoner van Losser en verwoed amateur-geoloog, kwam een groepje amateur-geologen op het idee om de door Staring blootgelegde zandsteenbank opnieuw te ontsluiten. Deze keer werd al op vier meter diepte de zandsteen aangetroffen. Eromheen werd door de gemeente een plantsoen aangelegd en W.C. H. Staring kreeg de eer die hem toekwam: de groeve werd naar hem vernoemd en bij de ingang kwam een borstbeeld van hem te staan. \n" +
                "Dr. Staringstraat, Losser. Bezoek na tel. overleg. 053-5385623. \n" +
                "zie ook artikel Zandsteen in Twente \n";
        String description9 = "Permanente expositie van fossielen, do, vrij 10:00-12:00, 14:00-17:00; zo 14:00-17:00 uur. \n";

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(title, "Boortoren");
//        contentValues.put(latitude, 52.243069);
//        contentValues.put(longitude, 6.799563);
//        contentValues.put(description, descriptionBoortoren);


        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Boortoren', 52.243069, 6.799563, '" + descriptionBoortoren + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Salt Museum', 52.263650, 6.712268, '" + description1 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Natura Docet Wonderryck Twente', 52.371659, 7.001318, '" + description2 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Stichting Veenmuseum', 52.447143, 6.653466, '" + description3 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('geologische route Sallandse Heuvelrug', 52.347848, 6.418660, '" + description4 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Envita Almelo B.V', 52.341766, 6.681827, '" + description5 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Natuurmuseum Enschede', 52.233029, 6.894875, '" + description6 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Anderson museum in Losser', 52.263837, 7.005692, '" + description7 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Staringmonument met zandsteengroeve in Losser', 52.256800, 7.006776, '" + description8 + "')");
        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Museum De Trilobiet in Weerselo', 52.336308, 6.879793, '" + description9 + "')");

//        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Test11', 52.263837, 7.005692, '" + description7 + "')");
//        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Test12', 52.256800, 7.006776, '" + description8 + "')");
//        db.execSQL("INSERT INTO geopoint (TITLE, LATITUDE, LONGITUDE, DESCRIPTION) VALUES ('Test13', 52.336308, 6.879793, '" + description9 + "')");

//        long result = db.insert(SPOT, null, contentValues);
//
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    public boolean addDefaultPics(SQLiteDatabase db) {
        Uri zoutwinning = getUriToDrawable(context, R.drawable.zoutwinning);
        Uri salt_house = getUriToDrawable(context, R.drawable.salt_house);
        Uri pic11 = getUriToDrawable(context, R.drawable.pic11);
        Uri pic21 = getUriToDrawable(context, R.drawable.pic21);
        Uri pic22 = getUriToDrawable(context, R.drawable.pic22);
        Uri pic31 = getUriToDrawable(context, R.drawable.pic31);
        Uri pic32 = getUriToDrawable(context, R.drawable.pic32);
        Uri pic4 = getUriToDrawable(context, R.drawable.pic4);
        Uri pic5 = getUriToDrawable(context, R.drawable.pic5);
        Uri pic61 = getUriToDrawable(context, R.drawable.pic61);
        Uri pic62 = getUriToDrawable(context, R.drawable.pic62);
        Uri pic71 = getUriToDrawable(context, R.drawable.pic71);
        Uri pic72 = getUriToDrawable(context, R.drawable.pic72);
        Uri pic81 = getUriToDrawable(context, R.drawable.pic81);
        Uri pic91 = getUriToDrawable(context, R.drawable.pic91);

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Boortoren', '" + zoutwinning.toString() + "')");
        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Boortoren', '" + salt_house.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Salt Museum', '" + pic11.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Natura Docet Wonderryck Twente', '" + pic21.toString() + "')");
        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Natura Docet Wonderryck Twente', '" + pic22.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Stichting Veenmuseum', '" + pic31.toString() + "')");
        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Stichting Veenmuseum', '" + pic32.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('geologische route Sallandse Heuvelrug', '" + pic4.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Envita Almelo B.V', '" + pic5.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Natuurmuseum Enschede', '" + pic61.toString() + "')");
        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Natuurmuseum Enschede', '" + pic62.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Anderson museum in Losser', '" + pic71.toString() + "')");
        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Anderson museum in Losser', '" + pic72.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Staringmonument met zandsteengroeve in Losser', '" + pic81.toString() + "')");

        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Museum De Trilobiet in Weerselo', '" + pic91.toString() + "')");

//        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Test11', '" + pic91.toString() + "')");
//        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Test13', '" + pic91.toString() + "')");
//        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Test12', '" + pic91.toString() + "')");

        return true;
    }

    public void addDefaultNews(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        String descriptionBoortoren = "New GeoPoint Boortoren is added in the app! Check on the GeoPoint list for further information and fun activities.\n\n" +
                "Boortoren is a drilling tower which was used back in around 1919 to drill holes in the ground and receive salt.\n\n" +
                "As time progressed, mobile drilling stations were created and this was replaced with smaller, cheaper and less striking salt houses above the caverns.";
        Uri zoutwinning = getUriToDrawable(context, R.drawable.zoutwinning);
        contentValues.put(title, "What is new at Boortoren?");
        contentValues.put(content, descriptionBoortoren);
        contentValues.put(newsPic, zoutwinning.toString());
//        db.execSQL("INSERT INTO picture (TITLE, VALUE) VALUES ('Boortoren', '" + zoutwinning.toString() + "')");

        db.insert(NEWS, null, contentValues);

    }


    public Cursor getCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + LOGIN, null);
        return data;
    }

    public Cursor getAccount(String email_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ACCOUNT + " WHERE EMAIL = '" + email_ + "'", null);
        return data;
    }

    public Cursor getAccount(String email_, String password_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ACCOUNT + " WHERE EMAIL = '" + email_ + "' AND PASSWORD = '" + password_ + "'", null);
        return data;
    }

    public boolean hasIcon(String email_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ICON FROM " + ACCOUNT + " WHERE EMAIL = '" + email_ + "' AND HASICON = 1", null);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Bitmap getIcon(String email_) {
        Cursor data = getAccount(email_);
        ArrayList<Bitmap> theList = new ArrayList<>();
        while (data.moveToNext()) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(data.getString(5)));
                theList.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return theList.get(0);
    }

    public void updateUserName(String email, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE " + ACCOUNT + " SET NAME = '" + newName + "' WHERE EMAIL = '" + email + "'";
        db.execSQL(update);
    }

    //TODO
    public void updateUserEmail(String email_, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(email, newEmail);
        db.update(ACCOUNT, cv, "EMAIL = '" + email_ + "'", null);
//        String update = "UPDATE " + ACCOUNT + " SET EMAIL = '" + newEmai + "' WHERE EMAIL = '" + email + "'";
//        db.execSQL(update);
    }

    public void updateUserPass(String email, String newPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE " + ACCOUNT + " SET PASSWORD = '" + newPass + "' WHERE EMAIL = '" + email + "'";
        db.execSQL(update);
    }

    public boolean updateUserIcon(String email, String newIcon) {
        SQLiteDatabase db = this.getWritableDatabase();
//        byte[] newIcon_ = getBytes(newIcon);
//        String update = "UPDATE " + ACCOUNT + " SET ICON = " + newIcon_ + " WHERE EMAIL = '" + email + "'";
//        db.execSQL(update);
        ContentValues cv = new ContentValues();
        cv.put(userIcon, newIcon);
        cv.put(hasIcon, 1);
        int rowCount = db.update(ACCOUNT, cv, "EMAIL = '" + email + "'", null);
        if (rowCount == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getFavoriteSpots(String email_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + STAR + " WHERE EMAIL = '" + email_ + "'", null);
        return data;
    }

    public boolean isFavoriteSpots(String email_, String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + STAR + " WHERE EMAIL = '" + email_ + "' AND TITLE = '" + title_ + "'", null);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setFavorite(String email_, String title_) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(email, email_);
        contentValues.put(title, title_);
        long result = db.insert(STAR, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void removeFavorite(String email_, String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STAR, "EMAIL = ? and TITLE = ?", new String[]{email_, title_});
    }

    public void removeGeoPoint(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SPOT, "TITLE = '" + title_ + "'", null);
        db.delete(STAR, "TITLE = '" + title_ + "'", null);
    }

    public void removeGeoPics(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PICTURE, "TITLE = '" + title_ + "'", null);
    }

    public Cursor getGeoPoints() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SPOT, null);
        return data;
    }

    public Cursor getSpot(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SPOT + " WHERE TITLE = '" + title_ + "'", null);
        return data;
    }

    public Cursor getNews(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + NEWS + " WHERE TITLE = '" + title_ + "'", null);
        return data;
    }

    public Cursor getNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + NEWS, null);
        return data;
    }

    public void removeNews(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NEWS, "TITLE = '" + title_ + "'", null);
    }
//
//    public ArrayList<Bitmap> getPictures(String title_) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor data = db.rawQuery("SELECT VALUE FROM " + PICTURE + " WHERE TITLE = '" + title_ + "'", null);
//        ArrayList<Bitmap> theList = new ArrayList<>();
//        while (data.moveToNext()) {
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(data.getString(0)));
//                theList.add(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return theList;
//    }

    public Uri getNewsPic(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT NEWSPIC FROM " + NEWS + " WHERE TITLE = '" + title_ + "'", null);
        ArrayList<Uri> theList = new ArrayList<>();
        while (data.moveToNext()) {
            theList.add(Uri.parse(data.getString(0)));
        }
        return theList.get(0);
    }

    public ArrayList<Uri> getPictures(String title_) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT VALUE FROM " + PICTURE + " WHERE TITLE = '" + title_ + "'", null);
        ArrayList<Uri> theList = new ArrayList<>();
        while (data.moveToNext()) {
            theList.add(Uri.parse(data.getString(0)));
        }
        return theList;
    }


    public void logout() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOGIN, "1", null);
    }

    public boolean login(String email_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(email, email_);
        long result = db.insert(LOGIN, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    // convert from bitmap to byte array
//    public byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }

//    // convert from byte array to bitmap
//    public Bitmap getBitMap(byte[] image) {
//        return BitmapFactory.decodeByteArray(image, 0, image.length);
//    }


    public boolean isUser(String email) {
        Cursor data = getAccount(email);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean correctPassword(String email, String password) {
        Cursor data = getAccount(email, password);
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getCurrent() {
        Cursor data = getCurrentUser();
        ArrayList<String> theList = new ArrayList<>();
        while (data.moveToNext()) {
            theList.add(data.getString(1));
        }
        return theList.get(0);
    }

    public String getUserName(String email) {
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = getAccount(email);
        while (data.moveToNext()) {
            theList.add(data.getString(2));
        }
        return theList.get(0);
    }

    public boolean isLogin() {
        Cursor data = getCurrentUser();
        if (data.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isAdmin(String email_) {
        Cursor data = getAccount(email_);
        ArrayList<Integer> theList = new ArrayList<>();
        while (data.moveToNext()) {
            theList.add(data.getInt(4));
        }
        if (theList.get(0) == 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * get uri to any resource type
     *
     * @param context - context
     * @param resId   - resource id
     * @return - Uri to resource by given id
     * @throws Resources.NotFoundException if the given ID does not exist.
     */
    public static final Uri getUriToResource(@NonNull Context context,
                                             @AnyRes int resId)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    /**
     * get uri to drawable or any other resource type if u wish
     *
     * @param context    - context
     * @param drawableId - drawable res id
     * @return - uri
     */
    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }


}
