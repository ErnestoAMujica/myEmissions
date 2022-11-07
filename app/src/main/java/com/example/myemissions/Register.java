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
        goToLogin = (Button) findViewById(R.id.register_goToLogin_button);
        db = new SQLiteInterface(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String retypedPass = retypedPassword.getText().toString();

                //Checking for blank input fields
                if(user.equals("") || pass.equals("") || retypedPass.equals(""))
                    Toast.makeText(Register.this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
                else {
                    if(!pass.equals(retypedPass))
                        Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                    else {
                        if (db.checkUsername(user))
                            Toast.makeText(Register.this, "User is already registered.", Toast.LENGTH_LONG).show();
                        else {

                            if (!db.insertData(user, pass))
                                Toast.makeText(Register.this, "ERROR: User \"" + user + "\" has not been registered.", Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(Register.this, "User \"" + user + "\" has been registered.", Toast.LENGTH_LONG).show();
                                Intent goToLogin = new Intent(getApplicationContext(), Login.class);
                                startActivity(goToLogin);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}