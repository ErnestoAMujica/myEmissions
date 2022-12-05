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
    Button profile_SignOutButton, settingsButton, homeButton;

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
        if (extras != null) {
            username = extras.getString("username");
            password = extras.getString("password");
        }

        usernameDisplay.setText(username);
        nameDisplay.setText(username);
        passwordDisplay.setText(password);

        profile_SignOutButton = (Button) findViewById(R.id.signOutButton);

        profile_SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLoginPage = new Intent(getApplicationContext(), Login.class);
                startActivity(goToLoginPage);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSettings = new Intent(getApplicationContext(), Preferences.class);
                goToSettings.putExtra("username", username);
                goToSettings.putExtra("password", password);
                startActivity(goToSettings);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                goToHome.putExtra("username", username);
                goToHome.putExtra("password", password);
                startActivity(goToHome);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}
