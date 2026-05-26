package com.example.app_thoi_trang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView img_bong_giay, img_tuGiay;
    TextView tv_loiChao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ép ứng dụng luôn ở chế độ sáng
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img_bong_giay = findViewById(R.id.img_bong_giay);
        img_tuGiay = findViewById(R.id.img_tuGiay);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_bong_giay);
        if (img_bong_giay != null) {
            img_bong_giay.setAnimation(animation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        },4500);
    }

}
