package com.example.week1project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ImageButton createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccount = findViewById(R.id.btnCreate);

        createAccount.setOnClickListener(new View.OnClickListener() {

            // Lets avoid the use of 'this' for the context
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateAccount.class);
                        startActivity(intent);
            }
        });
    }
}