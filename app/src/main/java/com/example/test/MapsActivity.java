package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test.accounts.AccountPage;
import com.example.test.accounts.Login;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "style problem";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private boolean pop;
    SqliteHelper dbHelper;
    HashMap<String, LatLng> markers;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        markers = new HashMap<>();
        dbHelper = new SqliteHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pop = getIntent().getBooleanExtra("POP_UP", false);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_news:
                        Intent intent1 = new Intent(MapsActivity.this, NewsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ic_list:
                        if (dbHelper.isLogin()) {
                            Intent intent2 = new Intent(MapsActivity.this, TestingActivity.class);
                            startActivity(intent2);
                            overridePendingTransition(0,0);
                        } else {
                            Intent intent2 = new Intent(MapsActivity.this, SpotlistActivity.class);
                            startActivity(intent2);
                            overridePendingTransition(0,0);
                        }
                        break;

                    case R.id.ic_Map:
//                        Intent intent3 = new Intent(MapsActivity.this, MapsActivity.class);
//                        startActivity(intent3);
                        break;

                    case R.id.ic_account:
                        if (dbHelper.isLogin()) {
                            Intent intent4 = new Intent(MapsActivity.this, AccountPage.class);
                            startActivity(intent4);
                            overridePendingTransition(0,0);
                        } else {
                            Intent intent4 = new Intent(MapsActivity.this, Login.class);
                            startActivity(intent4);
//                            overridePendingTransition(0,0);
                        }

                        break;
                }


                return false;
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        addMarkers();
        for (Map.Entry<String, LatLng> entry : markers.entrySet()) {
            mMap.addMarker(new MarkerOptions().position(entry.getValue()).title(entry.getKey()));
        }
        mMap.setOnMarkerClickListener(this);

        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        enableMyLocation();

        //set zoom
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            double longitude = location.getLongitude();
//            double latitude = location.getLatitude();
//            LatLng current = new LatLng(latitude, longitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));
//        } else {
//            LatLng zoom = new LatLng(52.3, 6.8);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 10));
//        }
        LatLng zoom = new LatLng(52.3, 6.8);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoom, 10));

        if (pop) {
            String title = getIntent().getStringExtra("TITLE");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(title), 12));
            Intent intent = new Intent(MapsActivity.this, PopUpWindow.class);
            intent.putExtra("TITLE", title);
            startActivity(intent);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12));
        Intent intent = new Intent(MapsActivity.this, PopUpWindow.class);
        intent.putExtra("TITLE", marker.getTitle());
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    public void addMarkers() {
        Cursor data = dbHelper.getGeoPoints();
        while (data.moveToNext()) {
            String title = data.getString(1);
            double lat = data.getDouble(2);
            double lng = data.getDouble(3);
            LatLng latLng = new LatLng(lat, lng);
            markers.put(title, latLng);
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
