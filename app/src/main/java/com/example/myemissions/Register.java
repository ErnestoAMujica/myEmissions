package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username, password, retypedPassword;
    Button register, goToLogin;
    SQLiteInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        retypedPassword = (EditText) findViewById(R.id.password_retype);
        register = (Button) findViewById(R.id.register_button);
        goToLogin = (Button) findViewById(R.id.register_button_GoToLogin);
        db = new SQLiteInterface(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String retypedPass = password.getText().toString();

                //Checking for blank input fields
                if(user.equals("") || pass.equals("") || retypedPass.equals(""))
                    Toast.makeText(Register.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                else {
                    if(!pass.equals(retypedPass))
                        Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_SHORT);
                    else {
                        if (db.checkUsername(user))
                            Toast.makeText(Register.this, "User is already registered.", Toast.LENGTH_SHORT);
                        else {

                            if (!db.insertData(user, pass))
                                Toast.makeText(Register.this, "ERROR: User \"" + user + "\" has not been registered.", Toast.LENGTH_SHORT);
                            else {
                                Toast.makeText(Register.this, "User \"" + user + "\" has been registered.", Toast.LENGTH_SHORT);
                                Intent goToLogin = new Intent(getApplicationContext(), Login.class);
                                startActivity(goToLogin);
                            }
                        }
                    }
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(goToLogin);
            }
        });
    }
}