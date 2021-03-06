package com.example.week1project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private ImageButton next;
    private Button back;

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
        back = findViewById(R.id.btnBack);

        validEmail = false;
        validPassword = false;
        validMatch = false;
        next.setEnabled(false);
        next.setColorFilter(Color.argb(100, 20, 20, 20));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
                        emailCheck.setTextColor(Color.RED);
                        emailExists = true;
                        next.setEnabled(false);
                        next.setColorFilter(Color.argb(100, 20, 20, 20));
                    }
                }

                if (!emailExists) {
                    if (!emailMatcher.matches()) {
                        emailCheck.setText("Invalid email");
                        emailCheck.setTextColor(Color.RED);
                        validEmail = false;
                        next.setEnabled(false);
                        next.setColorFilter(Color.argb(100, 20, 20, 20));
                    }
                    else {
                        emailCheck.setText("Valid email");
                        emailCheck.setTextColor(Color.GREEN);
                        validEmail = true;

                        if (validPassword && validMatch) {
                            next.setEnabled(true);
                            next.clearColorFilter();
                        }
                        else {
                            next.setEnabled(false);
                            next.setColorFilter(Color.argb(100, 20, 20, 20));
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
                inputConfirm = confirm.getText().toString();
                Pattern emailPattern = Pattern.compile(passwordValidation);
                Matcher emailMatcher = emailPattern.matcher(inputPassword);

                if (!emailMatcher.matches()) {
                    passwordCheck.setText("Invalid password");
                    passwordCheck.setTextColor(Color.RED);
                    validPassword = false;
                    next.setEnabled(false);
                    next.setColorFilter(Color.argb(100, 20, 20, 20));
                }
                else {
                    passwordCheck.setText("Valid password");
                    passwordCheck.setTextColor(Color.GREEN);
                    validPassword = true;

                    if (validEmail && validMatch) {
                        next.setEnabled(true);
                        next.clearColorFilter();
                    }
                    else {
                        next.setEnabled(false);
                        next.setColorFilter(Color.argb(100, 20, 20, 20));
                    }
                }

                if (!inputConfirm.equals(inputPassword)) {
                    confirmCheck.setText("Passwords do not match");
                    confirmCheck.setTextColor(Color.RED);
                    next.setEnabled(false);
                    next.setColorFilter(Color.argb(100, 20, 20, 20));
                }
                else {
                    confirmCheck.setText("Passwords match");
                    confirmCheck.setTextColor(Color.GREEN);
                    validMatch = true;

                    if (validEmail && validPassword) {
                        next.setEnabled(true);
                        next.setColorFilter(null);
                    }
                    else {
                        next.setEnabled(false);
                        next.setColorFilter(Color.argb(100, 20, 20, 20));
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
                    confirmCheck.setTextColor(Color.RED);
                    next.setEnabled(false);
                    next.setColorFilter(Color.argb(100, 20, 20, 20));
                }
                else {
                    confirmCheck.setText("Passwords match");
                    confirmCheck.setTextColor(Color.GREEN);
                    validMatch = true;

                    if (validEmail && validPassword) {
                        next.setEnabled(true);
                        next.setColorFilter(null);
                    }
                    else {
                        next.setEnabled(false);
                        next.setColorFilter(Color.argb(100, 20, 20, 20));
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
