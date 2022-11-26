package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    EditText username;
    TextInputEditText password;
    Button loginButton, forgotPasswordButton, registerPageButton;
    SQLiteInterface db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());

        username = (EditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPassword_button);
        registerPageButton = (Button) findViewById(R.id.login_signup_button);
        db = new SQLiteInterface(this);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(Login.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                else{
                    if(!db.checkUsernamePassword(user, pass))
                        Toast.makeText(Login.this, "Login failed, username and password combination does not exist.", Toast.LENGTH_SHORT).show();
                    else{
                        Toast.makeText(Login.this, "Successful login.", Toast.LENGTH_SHORT).show();
                        Intent goToHomePage = new Intent(getApplicationContext(), Home.class);
                        goToHomePage.putExtra("username", user);
                        startActivity(goToHomePage);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToResetPassPage = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(goToResetPassPage);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        registerPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToRegisterPage = new Intent(getApplicationContext(), Register.class);
                startActivity(goToRegisterPage);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}