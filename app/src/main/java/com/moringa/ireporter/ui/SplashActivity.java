package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringa.ireporter.MainActivity;
import com.moringa.ireporter.R;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    Animation anima, animaa;
    ImageView image;
    TextView splatext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        anima = AnimationUtils.loadAnimation(this, R.anim.animaa);
        animaa = AnimationUtils.loadAnimation(this, R.anim.animaa);

        image = findViewById(R.id.imageView);
        splatext = findViewById(R.id.splashText);

        image.setAnimation(animaa);
        splatext.setAnimation(animaa);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}