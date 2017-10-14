package com.codepath.earthquakemonitor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.earthquakemonitor.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    // Create the ParseUser
    ParseUser user;
    EditText etEmail;
    EditText etName;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.input_email);

        etName = findViewById(R.id.input_name);

        etPassword = findViewById(R.id.input_password);

        Button signUp = findViewById(R.id.btn_signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String userName = etName.getText().toString();
                String password = etPassword.getText().toString();
                if (email == null || userName == null || password == null) {
                    return;
                }
                user = new ParseUser();
                // Set core properties
                user.setUsername(userName);
                user.setPassword(password);
                user.setEmail(email);
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent i = new Intent(SignUpActivity.this, MapActivity.class);
                            startActivity(i);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
}
