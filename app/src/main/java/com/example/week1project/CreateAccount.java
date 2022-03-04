package com.example.week1project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    private EditText email;
    private TextView emailCheck;
    private boolean validEmail;
    private String inputEmail;
    boolean emailExists;
    private EditText password;
    private String inputPassword;
    private TextView passwordCheck;
    private boolean validPassword;
    private EditText confirm;
    private String inputConfirm;
    private TextView confirmCheck;
    private boolean validMatch;
    private Button next;

    private static final String emailValidation = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String passwordValidation = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        email = findViewById(R.id.etEmail);
        emailCheck = findViewById(R.id.tvEmailCheck);
        password = findViewById(R.id.etPassword);
        passwordCheck = findViewById(R.id.tvPasswordCheck);
        confirm = findViewById(R.id.etConfirm);
        confirmCheck = findViewById(R.id.tvConfirmCheck);
        next = findViewById(R.id.btnNext);

        validEmail = false;
        validPassword = false;
        validMatch = false;
        next.setEnabled(false);



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable s) {
                String currentEntry;
                emailExists = false;

                inputEmail = email.getText().toString();
                Pattern emailPattern = Pattern.compile(emailValidation);
                Matcher emailMatcher = emailPattern.matcher(inputEmail);

                SharedPreferences prefs = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                Map<String, ?> allEntries = prefs.getAll();

                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    currentEntry = entry.getValue().toString();

                    if (currentEntry.equals(inputEmail)) {
                        emailCheck.setText("Email already exists");
                        emailExists = true;
                        next.setEnabled(false);
                    }
                }

                if (!emailExists) {
                    if (!emailMatcher.matches()) {
                        emailCheck.setText("Invalid email");
                        validEmail = false;
                        next.setEnabled(false);
                    } else {
                        emailCheck.setText("Valid email");
                        validEmail = true;

                        if (validPassword && validMatch) {
                            next.setEnabled(true);
                        }
                        else {
                            next.setEnabled(false);
                        }
                    }
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable s) {
                inputPassword = password.getText().toString();
                Pattern emailPattern = Pattern.compile(passwordValidation);
                Matcher emailMatcher = emailPattern.matcher(inputPassword);

                if (!emailMatcher.matches()) {
                    passwordCheck.setText("Invalid password");
                    validPassword = false;
                    next.setEnabled(false);
                }
                else {
                    passwordCheck.setText("Valid password");
                    validPassword = true;

                    if (validEmail && validMatch) {
                        next.setEnabled(true);
                    }
                    else {
                        next.setEnabled(false);
                    }
                }
            }
        });

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable s) {
                inputConfirm = confirm.getText().toString();

                if (!inputConfirm.equals(inputPassword)) {
                    confirmCheck.setText("Passwords do not match");
                    next.setEnabled(false);
                }
                else {
                    validMatch = true;
                    confirmCheck.setText("Passwords match");

                    if (validEmail && validPassword) {
                        next.setEnabled(true);
                    }
                    else {
                        next.setEnabled(false);
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putString("email", inputEmail);
                prefEditor.commit();
                Toast.makeText(CreateAccount.this, "Account created", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
