package com.moringa.ireporter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringa.ireporter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static int SPLASH_SCREEN = 5000;

    Animation anima, animaa;
    ImageView image;
    TextView splatext;
    @BindView(R.id.loginregister) TextView mLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginRegister.setOnClickListener(this);

        anima = AnimationUtils.loadAnimation(this, R.anim.animaa);
        animaa = AnimationUtils.loadAnimation(this, R.anim.animaa);

        image = findViewById(R.id.loginImage);
        splatext = findViewById(R.id.loginregister);

        image.setAnimation(animaa);
        splatext.setAnimation(animaa);
    }

    @Override
    public void onClick(View view) {
        if (view == mLoginRegister) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }

    }
}