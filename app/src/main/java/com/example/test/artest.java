package com.example.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class artest extends AppCompatActivity implements Scene.OnUpdateListener {

    private ArSceneView arView;
    private Session session;
    private boolean shouldConfigureSession = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artest);

        arView = (ArSceneView) findViewById(R.id.arView);
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                setupSession();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(artest.this, "Permission need to display camera", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();

        initSceneView();
    }

    private void initSceneView() {

        arView.getScene().addOnUpdateListener(this);
    }

    private void setupSession() {
        if(session == null){
            try {
                session = new Session(this);
            } catch (UnavailableArcoreNotInstalledException e) {
                e.printStackTrace();
            } catch (UnavailableApkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableSdkTooOldException e) {
                e.printStackTrace();
            } catch (UnavailableDeviceNotCompatibleException e) {
                e.printStackTrace();
            }

            shouldConfigureSession = true;



        }
        if(shouldConfigureSession){
            configSession();
            shouldConfigureSession = false;
            arView.setupSession(session);
        }

        try {
            session.resume();
            arView.resume();
        } catch (CameraNotAvailableException e) {
            e.printStackTrace();
            session = null;
            return;
        }


    }

    private void configSession() {
        Config config  = new Config(session);
        if(!buildDatabase(config)){
            Toast.makeText(this, "error database", Toast.LENGTH_SHORT).show();
        }
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
    }

    private boolean buildDatabase(Config config) {
        AugmentedImageDatabase augmentedImageDatabase;
        Bitmap bitmap = loadImage();
        if(bitmap == null)
            return false;

        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("lion", bitmap);
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }

    private Bitmap loadImage() {
        try {
            InputStream is = getAssets().open("lion_qr.jpeg");
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arView.getArFrame();
        Collection<AugmentedImage> updateAugmentedImg = frame.getUpdatedTrackables(AugmentedImage.class);

        for(AugmentedImage image:updateAugmentedImg){
            if(image.getTrackingState() == TrackingState.TRACKING){
                if(image.getName().equals("lion")){
                    MyARNode node = new MyARNode(this,R.raw.lion);
                    node.setImage(image);
                    arView.getScene().addChild(node);
                }
            }
        }
    }


    protected void onResume() {

        super.onResume();
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                setupSession();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(artest.this, "Permission need to display camera", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
    }

    protected void onPause() {
        super.onPause();
        if(session !=null){
            arView.pause();
            session.pause();
        }
    }

}
