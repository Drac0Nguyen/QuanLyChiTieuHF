package com.example.doanandroid.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.R;

public class IntroActivity extends AppCompatActivity {
    public static int Splash_Screen = 5000;
    Animation topAnim,bottomAnim;
    ImageView img;
    TextView wellcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        // Animation
        topAnim    = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        // hooks
        img     = findViewById(R.id.imageView);
        wellcom = findViewById(R.id.txtView);

        //
        img.setAnimation(topAnim);
        wellcom.setAnimation(bottomAnim);

        // next frame

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },Splash_Screen);
    }
}