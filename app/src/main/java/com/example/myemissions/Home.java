package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /*  Unused, page/activity switching is done using listeners in their respective
        files.
    //called when user presses the login button in login page
    public void transferHome(View view) {
        // send user to the home page...
    }
    */

}