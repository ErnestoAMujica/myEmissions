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

public class ResetPassword extends AppCompatActivity {

    EditText username;
    TextInputEditText password, retypedPassword;
    Button resetPassword, goToLogin;
    TextView errorText;
    SQLiteInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());

        username = (EditText) findViewById(R.id.resetpassword_username);
        password = (TextInputEditText) findViewById(R.id.resetpassword_password);
        retypedPassword = (TextInputEditText) findViewById(R.id.password_retype);
        resetPassword = (Button) findViewById(R.id.resetpassword_button);
        goToLogin = (Button) findViewById(R.id.resetpassword_button_GoToLogin);
        errorText = (TextView) findViewById(R.id.resetpassword_error_text);
        db = new SQLiteInterface(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
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
                    Matcher passHasIllegal = illegal.matcher(pass);

                    if(pass.length() < 8 || !hasLetter.find() || !hasDigit.find() || !hasSpecial.find())
                        errorText.setText("Password must be longer than 8 characters and must contain a letter, number, and special character.");
                    else if(passHasIllegal.find())
                        errorText.setText("Password contains an illegal character such as a whitespace character, quotation mark, or semicolon.");
                    else if(pass.length() > 32)
                        errorText.setText("Password must not exceed 32 characters.");
                    else
                    {
                        if(!pass.equals(retypedPass))
                            errorText.setText("Passwords do not match.");
                        else {
                            if (!db.checkUsername(user))
                                errorText.setText("User does not exist.");
                            else {
                                errorText.setText("");
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