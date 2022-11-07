package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button loginButton, forgotPasswordButton, registerPageButton;
    SQLiteInterface db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
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
                        startActivity(goToHomePage);
                    }
                }
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToResetPassPage = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(goToResetPassPage);
            }
        });

        registerPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent goToRegisterPage = new Intent(getApplicationContext(), Register.class);
                startActivity(goToRegisterPage);
            }
        });
    }
}