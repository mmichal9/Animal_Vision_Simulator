package com.michal.animalvision;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VisionActivity extends AppCompatActivity {

    //TextView VisionText = (TextView) findViewById(R.id.visionTitle);
    //String VisionTitle = getIntent().getStringExtra(MainActivity.EXTRA_ITEM_TITLE);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);



        // example of creating the link button
        Button buttonBtn = (Button) findViewById(R.id.button);

        buttonBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mmichal.com/pages/Final_Projectv7.html#text-separator-bee"));
                startActivity(i);
            }
        });
    }

}
