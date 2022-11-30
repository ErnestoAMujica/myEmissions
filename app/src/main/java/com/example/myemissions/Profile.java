package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Profile extends AppCompatActivity {

    TextView usernameDisplay;
    TextView passwordDisplay;
    TextView nameDisplay;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());


        usernameDisplay = findViewById(R.id.username_display);
        passwordDisplay = findViewById(R.id.password_display);
        nameDisplay = findViewById(R.id.name_display);

        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        }
        catch (Exception e){
            username = "TestUser";
        }

        try {
            password = extras.getString("password", "Password123");
        }
        catch (Exception e){
            password = "TestPassword";
        }

        usernameDisplay.setText(username);
        nameDisplay.setText(username);
        passwordDisplay.setText(password);

    }
}
