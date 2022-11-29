package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    TextView emissionsTotal, usernameDisplay, currentDate;
    CardView addEmissionAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        Bundle extras = getIntent().getExtras();

        emissionsTotal = (TextView) findViewById(R.id.mainPage_emissionsDisplayNumber);
        usernameDisplay = (TextView) findViewById(R.id.mainPage_name);
        currentDate = (TextView) findViewById(R.id.mainPage_date);
        addEmissionAction = findViewById(R.id.mainPage_AddNewEmmissionDisplayCard);

        String currentDateString = java.text.DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        currentDate.setText(currentDateString);
        usernameDisplay.setText("Hi, " + extras.getString("username", "SampleName") + "!");

        addEmissionAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAddEmission = new Intent(getApplicationContext(), AddEmission.class);
                startActivity(goToAddEmission);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

}