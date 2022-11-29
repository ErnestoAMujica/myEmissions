package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class AddEmission extends AppCompatActivity {

    String username;
    Button tempButton;
    FileInterface emissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemission);

        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        }
        catch (Exception e){
            username = "TestUser";
        }

        emissions = new FileInterface(this, username);
        tempButton = (Button) findViewById(R.id.AddEmission_TEMPBUTTON);

        tempButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                emissions.addNewEmission(EmissionCalculator.SourceName.electric_medium, 5.3, "miles");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();
        try {
            username = extras.getString("username", "SampleName");
        }
        catch (Exception e){
            username = "TestUser";
        }

        Intent goToHomePage = new Intent(getApplicationContext(), Home.class);
        goToHomePage.putExtra("username", username);
        startActivity(goToHomePage);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}