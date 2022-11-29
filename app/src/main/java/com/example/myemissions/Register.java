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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText username;
    TextInputEditText password, retypedPassword;
    Button register, goToLogin;
    TextView errorText;
    SQLiteInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());

        username = (EditText) findViewById(R.id.register_username);
        password = (TextInputEditText) findViewById(R.id.register_password);
        retypedPassword = (TextInputEditText) findViewById(R.id.password_retype);
        register = (Button) findViewById(R.id.register_button);
        goToLogin = (Button) findViewById(R.id.register_goToLogin_button);
        errorText = (TextView) findViewById(R.id.error_text);
        db = new SQLiteInterface(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String retypedPass = retypedPassword.getText().toString();

                //Checking for blank input fields
                if(user.equals("") || pass.equals("") || retypedPass.equals(""))
                {
                    errorText.setText("Please fill out all fields.");
                }
                else {

                    Pattern letter = Pattern.compile("[a-zA-z]");
                    Pattern digit = Pattern.compile("[0-9]");
                    Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
                    Pattern illegal = Pattern.compile ("[ ;\"]");
                    Matcher hasLetter = letter.matcher(pass);
                    Matcher hasDigit = digit.matcher(pass);
                    Matcher hasSpecial = special.matcher(pass);
                    Matcher userHasIllegal = illegal.matcher(user);
                    Matcher passHasIllegal = illegal.matcher(pass);

                    if(pass.length() < 8 || !hasLetter.find() || !hasDigit.find() || !hasSpecial.find())
                    {
                        errorText.setText("Password must be longer than 8 characters and must contain a letter, number, and special character.");
                    }
                    else if(userHasIllegal.find() || passHasIllegal.find())
                        errorText.setText("Username or password contains an illegal character such as a whitespace character, quotation mark, or semicolon.");
                    else if(pass.length() > 32 || user.length() > 32)
                        errorText.setText("Username and password must not exceed 32 characters.");
                    else
                    {
                        if(!pass.equals(retypedPass))
                            errorText.setText("Passwords do not match.");
                        else {
                            if (db.checkUsername(user))
                                errorText.setText("User is already registered.");
                            else {
                                errorText.setText("");
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