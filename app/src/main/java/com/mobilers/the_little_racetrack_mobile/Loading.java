package com.mobilers.the_little_racetrack_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mobilers.the_little_racetrack_mobile.Constants.AuthenConstants;

public class Loading extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView icon_loading=  findViewById(R.id.loadingImageView);
        Glide.with(this)
                .load(R.drawable.race_car_animated)
                .into(icon_loading);

        progressBar = findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                float screenWidth = displayMetrics.widthPixels;
                while (progressStatus <101) {
                    float currentTranslationX = icon_loading.getTranslationX();
                    float newTranslationX = currentTranslationX + (0.01f * screenWidth);
                    icon_loading.setTranslationX(newTranslationX);
                        progressStatus ++;


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if(progressBar.getProgress() == 100) {
                                Intent resultIntent = new Intent();
                                setResult(AuthenConstants.LOADING_RESULT_CODE, resultIntent);
                                finish();
                            }

                        }
                    });

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

}