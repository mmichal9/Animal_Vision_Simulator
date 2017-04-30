package com.michal.animalvision;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityVision extends AppCompatActivity {
    //private static final int REQUEST_CAMERA_PERMISSION = 101;
    private CameraRenderer renderer;
    private TextureView textureView;
    private int filterId = R.id.filter0;

    public int filterCase(String VisionTitle) {
        int filter = 0;

        switch (VisionTitle) {
            case "Black and White":
                filter = R.id.filter1;
                break;
            case "Fish Vision":
                filter = R.id.filter3;
                break;
            case "HSV to RGB":
                filter = R.id.filter4;
                break;
            case "Insect Vision":
                filter = R.id.filter5;
                break;
            case "Invert Vision":
                filter = R.id.filter6;
                break;
            case "Original Vision":
                filter = R.id.filter0;
                break;
            case "Test Vision":
                filter = R.id.filter7;
                break;
            case "VR Dog Vision":
                filter = R.id.filter17;
                break;


            case "Color Blindness - Deuteranomaly":
                filter = R.id.filter9;
                break;
            case "Color Blindness - Deuteranopia":
                filter = R.id.filter10;
                break;
            case "Color Blindness - Protanomaly":
                filter = R.id.filter11;
                break;
            case "Color Blindness - Protanopia":
                filter = R.id.filter12;
                break;
            case "Color Blindness - Tritanomaly":
                filter = R.id.filter13;
                break;
            case "Color Blindness - Tritanopia":
                filter = R.id.filter14;
                break;
            case "Color Blindness - Achromatomaly":
                filter = R.id.filter15;
                break;
            case "Color Blindness - Achromatosopia":
                filter = R.id.filter16;
                break;


            case "#text-separator-snake":
                filter = R.id.filter8;
                break;
            case "#text-separator-dog":
                filter = R.id.filter2;
                break;
            case "#text-separator-snail":
                filter = R.id.filter18;
                break;
            case "#text-separator-cat":
                filter = R.id.filter20;
                break;
            case "#text-separator-bird":
                filter = R.id.filter21;
                break;
            case "#text-separator-bee":
                filter = R.id.filter22;
                break;
            case "#text-separator-clam":
                filter = R.id.filter23;
                break;
            case "#text-separator-gecko":
                filter = R.id.filter24;
                break;
            case "#text-separator-cuttlefish":
                filter = R.id.filter25;
                break;
            case "#text-separator-spider":
                filter = R.id.filter26;
                break;
            case "#text-separator-shark":
                filter = R.id.filter19;
                break;
            case "#text-separator-bear":
                filter = R.id.filter27;
                break;
            case "#text-separator-ape":
                filter = R.id.filter28;
                break;
            case "#text-separator-horse":
                filter = R.id.filter29;
                break;
            case "#text-separator-octopus":
                filter = R.id.filter30;
                break;
            case "#text-separator-dolphin":
                filter = R.id.filter31;
                break;
        }
        return filter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        String VisionTitle = getIntent().getStringExtra(MainActivity.EXTRA_ITEM_TITLE);

        filterId = filterCase(VisionTitle);

        textureView = (TextureView) findViewById(R.id.textureView);
        ImageButton catchBtn = (ImageButton) findViewById(R.id.catchBtn);
        ImageButton otherBtn = (ImageButton) findViewById(R.id.otherBtn);
        ImageButton pageBtn = (ImageButton) findViewById(R.id.pageBtn);

        catchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }

        });

        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapcamera();
            }

        });

        pageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadpage();
            }

        });

        setupCameraPreviewView();
    }


//        RequestPermission moved to the main activity to avoid app restart after permission granting!!!!!!!!!!!!!!!!
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                Toast.makeText(this, "Camera access is required.", Toast.LENGTH_SHORT).show();
//
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
//                        REQUEST_CAMERA_PERMISSION);
//            }
//        }
//        else{
//              setupCameraPreviewView();
//        }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CAMERA_PERMISSION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    setupCameraPreviewView();
//                }
//            }
//        }
//    }

    void setupCameraPreviewView() {
        renderer = new CameraRenderer(this);


        assert textureView != null;

        textureView.setSurfaceTextureListener(renderer);

        // Show original frame when touch the view
        renderer.setSelectedFilter(filterId);


        textureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                renderer.onSurfaceTextureSizeChanged(null, v.getWidth(), v.getHeight());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        filterId = item.getItemId();
        setTitle(item.getTitle());

        if (renderer != null)
            renderer.setSelectedFilter(filterId);

        return true;
    }

    private boolean capture() {

        Bitmap bitmap = textureView.getBitmap();
        ImageSaver imageSaver = new ImageSaver(getApplicationContext());
        imageSaver.setExternal(true);
        imageSaver.setFileName(System.currentTimeMillis() + "image.jpg");
        if (isStoragePermissionGranted()) {
            imageSaver.save(bitmap);
        }

        return true;

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private String genSaveFileName(String prefix, String suffix) {
        Date date = new Date();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String timeString = dateformat1.format(date);
        String externalPath = Environment.getExternalStorageDirectory().toString();
        return externalPath + "/" + prefix + timeString + suffix;
    }


    private boolean swapcamera() {
        switch (renderer.getCameraOrient()) {
            case "Front":
                Log.d("test","test");
                renderer.setCameraOrient("Back");
                textureView.setSurfaceTextureListener(renderer);

                break;

            case "Back":

                renderer.setCameraOrient("Front");
                textureView.setSurfaceTextureListener(renderer);


                break;

        }


        textureView.setSurfaceTextureListener(renderer);


        // Show original frame when touch the view
        renderer.setSelectedFilter(filterId);


        return true;

    }


    private boolean loadpage() {

        String VisionTitle = getIntent().getStringExtra(MainActivity.EXTRA_ITEM_TITLE);

        String page = new String("http://mmichal.com/pages/Animal_resources_m.html");
        String loadPage = page + VisionTitle;

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(loadPage));

        String sub = VisionTitle.substring(0, 1);

        if (sub.equals("#")) {
            startActivity(i);
            //Toast.makeText(this, loadPage, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, VisionTitle, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, sub, Toast.LENGTH_SHORT).show();
        }

        //toast text test
        //Toast.makeText(this, "page loading.", Toast.LENGTH_SHORT).show();

        return true;

    }


}
