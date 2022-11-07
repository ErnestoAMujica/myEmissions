package com.example.myemissions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    EditText username, password, retypedPassword;
    Button resetPassword, goToLogin;
    SQLiteInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        username = (EditText) findViewById(R.id.resetpassword_username);
        password = (EditText) findViewById(R.id.resetpassword_password);
        retypedPassword = (EditText) findViewById(R.id.password_retype);
        resetPassword = (Button) findViewById(R.id.resetpassword_button);
        goToLogin = (Button) findViewById(R.id.resetpassword_button_GoToLogin);
        db = new SQLiteInterface(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String retypedPass = retypedPassword.getText().toString();

                //Checking for blank input fields
                if(user.equals("") || pass.equals("") || retypedPass.equals(""))
                    Toast.makeText(ResetPassword.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                else {
                    if(!pass.equals(retypedPass))
                        Toast.makeText(ResetPassword.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    else {
                        if (!db.checkUsername(user))
                            Toast.makeText(ResetPassword.this, "User does not exist.", Toast.LENGTH_LONG).show();
                        else {
                            if (!db.changePassword(user, pass))
                                Toast.makeText(ResetPassword.this, "ERROR: User \"" + user + "\" has failed to change password.", Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(ResetPassword.this, "User \"" + user + "\" has successfully changed their password.", Toast.LENGTH_LONG).show();
                                Intent goToLogin = new Intent(getApplicationContext(), Login.class);
                                startActivity(goToLogin);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}