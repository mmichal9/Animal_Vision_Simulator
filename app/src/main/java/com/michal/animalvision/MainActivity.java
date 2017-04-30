/*
 * Copyright 2016 nekocode
 * @author nekocode (nekocode.cn@gmail.com)
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package com.michal.animalvision;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;

    public static final String EXTRA_ITEM_TITLE = "extra.item.title";
    public static final String BLACK_AND_WHITE = "Black and White";
    public static final String FISH_VISION = "Fish Vision";
    public static final String HSV_TO_RGB = "HSV to RGB";
    public static final String INSECT_VISION = "Insect Vision";
    public static final String INVERT_VISION = "Invert Vision";
    public static final String ORIGINAL_VISION = "Original Vision";
    public static final String TEST_VISION = "Test Vision";
    public static final String VR_DOG_VISION = "VR Dog Vision";


    public static final String THERMAL_VISION = "#text-separator-snake";
    public static final String DOG_VISION = "#text-separator-dog";
    public static final String SNAIL_VISION = "#text-separator-snail";
    public static final String CAT_VISION = "#text-separator-cat";
    public static final String BIRD_VISION = "#text-separator-bird";
    public static final String BEE_VISION = "#text-separator-bee";
    public static final String CLAM_VISION = "#text-separator-clam";
    public static final String GECKO_VISION = "#text-separator-gecko";
    public static final String CUTTLEFISH_VISION = "#text-separator-cuttlefish";
    public static final String JUMPINGSPIDER_VISION = "#text-separator-spider";

    public static final String SHARK_VISION = "#text-separator-shark";
    public static final String BEAR_VISION = "#text-separator-bear";
    public static final String APE_VISION = "#text-separator-ape";
    public static final String HORSE_VISION = "#text-separator-horse";
    public static final String OCTOPUS_VISION = "#text-separator-octopus";
    public static final String DOLPHIN_VISION = "#text-separator-dolphin";



    public static final String DEUTERANOPIA = "Color Blindness - Deuteranopia";
    public static final String DEUTERANOMALY = "Color Blindness - Deuteranomaly";
    public static final String PROTANOPIA = "Color Blindness - Protanopia";
    public static final String PROTANOMALY = "Color Blindness - Protanomaly";
    public static final String TRITANOPIA = "Color Blindness - Tritanopia";
    public static final String TRITANOMALY = "Color Blindness - Tritanomaly";
    public static final String ACHROMATOSOPIA = "Color Blindness - Achromatosopia";
    public static final String ACHROMATOMALY = "Color Blindness - Achromatomaly";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Camera access is required.", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }
        }




        LinearLayout blackandWhiteBtn = (LinearLayout) findViewById(R.id.blackandWhiteBtn);
        LinearLayout dogVisionBtn = (LinearLayout) findViewById(R.id.dogVisionBtn);
        LinearLayout fishVisionBtn = (LinearLayout) findViewById(R.id.fishVisionBtn);
        LinearLayout hsvtorgbBtn = (LinearLayout) findViewById(R.id.hsvtorgbBtn);
        LinearLayout insectVisionBtn = (LinearLayout) findViewById(R.id.insectVision);
        LinearLayout invertVisionBtn = (LinearLayout) findViewById(R.id.invertVisionBtn);
        LinearLayout originalVisionBtn = (LinearLayout) findViewById(R.id.originalBtn);
        LinearLayout testVisionBtn = (LinearLayout) findViewById(R.id.testVisionBtn);
        LinearLayout thermalVisionBtn = (LinearLayout) findViewById(R.id.thermalVisionBtn);
        LinearLayout dog_VRBtn = (LinearLayout) findViewById(R.id.dog_VRBtn);
        LinearLayout snailBtn = (LinearLayout) findViewById(R.id.snailVisionBtn);
        LinearLayout sharkVisionBtn = (LinearLayout) findViewById(R.id.sharkVisionBtn);
        LinearLayout catVisionBtn = (LinearLayout) findViewById(R.id.catVisionBtn);
        LinearLayout birdVisionBtn = (LinearLayout) findViewById(R.id.birdVisionBtn);
        LinearLayout beeVisionBtn = (LinearLayout) findViewById(R.id.beeVisionBtn);
        LinearLayout clamVisionBtn = (LinearLayout) findViewById(R.id.clamVisionBtn);
        LinearLayout geckoVisionBtn = (LinearLayout) findViewById(R.id.geckoVisionBtn);
        LinearLayout cuttlefishVisionBtn = (LinearLayout) findViewById(R.id.cuttlefishVisionBtn);
        LinearLayout jumpingSpiderVisionBtn = (LinearLayout) findViewById(R.id.jumpingSpiderVisionBtn);
        LinearLayout bearVisionBtn = (LinearLayout) findViewById(R.id.bearVisionBtn);
        LinearLayout apeVisionBtn = (LinearLayout) findViewById(R.id.apeVisionBtn);
        LinearLayout horseVisionBtn = (LinearLayout) findViewById(R.id.horseVisionBtn);
        LinearLayout octopusVisionBtn = (LinearLayout) findViewById(R.id.octopusVisionBtn);
        LinearLayout dolphinVisionBtn = (LinearLayout) findViewById(R.id.dolphinVisionBtn);


        LinearLayout deuteranopiaBtn = (LinearLayout) findViewById(R.id.deuteranopiaBtn);
        LinearLayout deuteranomalyBtn = (LinearLayout) findViewById(R.id.deuteranomalyBtm);
        LinearLayout protanopiaBtn = (LinearLayout) findViewById(R.id.protanopiaBtn);
        LinearLayout protanomalyBtn = (LinearLayout) findViewById(R.id.protanomalyBtn);
        LinearLayout tritanopiaBtn = (LinearLayout) findViewById(R.id.tritanopiaBtn);
        LinearLayout tritanomalyBtn = (LinearLayout) findViewById(R.id.tritanomalyBtn);
        LinearLayout achromatosopiaBtn = (LinearLayout) findViewById(R.id.achromatosopiaBtn);
        LinearLayout achromatomalyBtn = (LinearLayout) findViewById(R.id.achromatomalyBtn);



        dogVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.DOG_VISION);
            }
        });
        blackandWhiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.BLACK_AND_WHITE);
            }
        });
        fishVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.FISH_VISION);
            }
        });
        hsvtorgbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.HSV_TO_RGB);
            }
        });
        insectVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.INSECT_VISION);
            }
        });
        invertVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.INVERT_VISION);
            }
        });
        originalVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.ORIGINAL_VISION);
            }
        });
        testVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.TEST_VISION);
            }
        });
        thermalVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.THERMAL_VISION);
            }
        });
        dog_VRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.VR_DOG_VISION);
            }
        });
        snailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.SNAIL_VISION);
            }
        });
        sharkVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.SHARK_VISION);
            }
        });
        catVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.CAT_VISION);
            }
        });
        birdVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.BIRD_VISION);
            }
        });
        beeVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.BEE_VISION);
            }
        });
        clamVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.CLAM_VISION);
            }
        });
        geckoVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.GECKO_VISION);
            }
        });
        cuttlefishVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.CUTTLEFISH_VISION);
            }
        });
        jumpingSpiderVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.JUMPINGSPIDER_VISION);
            }
        });
        bearVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.BEAR_VISION);
            }
        });
        apeVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.APE_VISION);
            }
        });
        horseVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.HORSE_VISION);
            }
        });
        octopusVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.OCTOPUS_VISION);
            }
        });
        dolphinVisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.DOLPHIN_VISION);
            }
        });


        deuteranopiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.DEUTERANOPIA);
            }
        });
        deuteranomalyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.DEUTERANOMALY);
            }
        });
        protanopiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.PROTANOPIA);
            }
        });
        protanomalyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.PROTANOMALY);
            }
        });
        tritanopiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.TRITANOPIA);
            }
        });
        tritanomalyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.TRITANOMALY);
            }
    });
        achromatosopiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.ACHROMATOSOPIA);
            }
        });
        achromatomalyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVisionActivity(MainActivity.ACHROMATOMALY);
            }
        });


    }

    private void loadVisionActivity(String visionTitle) {
        Intent intent = new Intent(MainActivity.this, MainActivityVision.class);   //VisionActivity
        intent.putExtra(MainActivity.EXTRA_ITEM_TITLE, visionTitle);
        startActivity(intent);

    }



}

