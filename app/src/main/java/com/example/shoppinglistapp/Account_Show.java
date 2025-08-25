/*
package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Account_Show extends AppCompatActivity {

    TextView textViewName, textViewEmail;
    Button btnLogout, edit;
    ImageView backBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);

        textViewName = findViewById(R.id.nameField);
        textViewEmail = findViewById(R.id.emailField);
        btnLogout = findViewById(R.id.logoutButton);
        edit = findViewById(R.id.edit);
        backBtn = findViewById(R.id.backButton);

        // Set user details (replace with Firebase user details)
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            textViewName.setText("Name: " + mAuth.getCurrentUser().getDisplayName());
            textViewEmail.setText("Email: " + mAuth.getCurrentUser().getEmail());
        }

        // Logout functionality
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut(); // Firebase logout
            Intent intent = new Intent(Account_Show.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear all activities
            startActivity(intent);
            finishAffinity(); // Close the app entirely
        });

        // Navigate to edit account screen
        edit.setOnClickListener(v -> {
            Intent i = new Intent(Account_Show.this, Account_Edit.class);
            startActivity(i);
        });

        // Back button functionality
        backBtn.setOnClickListener(v -> finish());
    }
}*/


package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Account_Show extends AppCompatActivity {

    TextView textViewName, textViewEmail;
    Button btnLogout;
    //Button edit;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_show);

        textViewName = findViewById(R.id.nameField);
        textViewEmail = findViewById(R.id.emailField);
        btnLogout = findViewById(R.id.logoutButton);
        //edit = findViewById(R.id.edit);
        backBtn = findViewById(R.id.backButton);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            textViewName.setText("Name: " + mAuth.getCurrentUser().getDisplayName());
            textViewEmail.setText("Email: " + mAuth.getCurrentUser().getEmail());
        }

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(Account_Show.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finishAffinity();
        });

        /*edit.setOnClickListener(v -> {
            Intent i = new Intent(Account_Show.this, Account_Edit.class);
            startActivity(i);
        });*/

        backBtn.setOnClickListener(v -> finish());
    }
}
