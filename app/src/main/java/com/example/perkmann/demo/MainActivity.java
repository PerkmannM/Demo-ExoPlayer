package com.example.perkmann.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by Perkmann on 06.10.16.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    Button btnStartVideo1;
    Button btnStartVideo2;
    Button btnStartVideo3;
    Button btnStartVideo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnStartVideo1 = (Button) findViewById(R.id.startVideo1);
        btnStartVideo2 = (Button) findViewById(R.id.startVideo2);
        btnStartVideo3 = (Button) findViewById(R.id.startVideo3);
        btnStartVideo4 = (Button) findViewById(R.id.startVideo4);
        btnStartVideo1.setOnClickListener(this);
        btnStartVideo2.setOnClickListener(this);
        btnStartVideo3.setOnClickListener(this);
        btnStartVideo4.setOnClickListener(this);
    }

    public void startIntent(String videoUrl) {
        Intent intent = PlayerActivity.build(getApplicationContext(), videoUrl);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startVideo1:
                startIntent("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4");
                break;
            case R.id.startVideo2:
                startIntent("http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_5mb.mp4");
                break;
            case R.id.startVideo3:
                startIntent("http://www.sample-videos.com/video/mp4/480/big_buck_bunny_480p_5mb.mp4");
                break;
            case R.id.startVideo4:
                startIntent("http://www.sample-videos.com/video/mp4/360/big_buck_bunny_360p_10mb.mp4");
                break;
        }
    }
}

