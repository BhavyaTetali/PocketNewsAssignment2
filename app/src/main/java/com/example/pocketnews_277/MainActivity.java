package com.example.pocketnews_277;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketnews_277.viewmodel.Login;

public class MainActivity extends AppCompatActivity {

    public static int TITLE_SPLASH_SCREEN = 5000;
    Animation titleAnimation;
    TextView appTitle, tagLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        // add animation for splash screen
        titleAnimation = AnimationUtils.loadAnimation(this,R.anim.title_animation);
        appTitle = findViewById(R.id.appTitle);
        tagLine = findViewById(R.id.tagLine);
        appTitle.setAnimation(titleAnimation);
        tagLine.setAnimation(titleAnimation);

        // handler to call the next activity : Login and signup screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashScreenIntent = new Intent(MainActivity.this, Login.class);
                startActivity(splashScreenIntent);
                finish();

            }
        },TITLE_SPLASH_SCREEN);




    }
}