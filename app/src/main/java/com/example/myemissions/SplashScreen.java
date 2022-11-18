package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Intent goToLogin = new Intent(SplashScreen.this, Login.class);
                startActivity(goToLogin);
                finish();
            }
        }, 2000);
    }
}