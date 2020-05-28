package com.example.hp_awareness_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ImageView main = (ImageView) findViewById(R.id.final_img);
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
        main.animate().alpha(1).setDuration(3500);
        new CountDownTimer(3500, 1000) { // 5000 = 5 sec

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                main.animate().translationYBy(-450).setDuration(800);
            }
        }.start();
        new CountDownTimer(4500, 1000) { // 5000 = 5 sec

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                frameLayout.animate().alpha(1).setDuration(1000);
            }
        }.start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this,PhoneActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        },8000);
    }
}
