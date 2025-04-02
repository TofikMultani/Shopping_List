package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    private EditText lem, lps; // Input fields for email and password
    private Button lbt; // Login button
    private TextView tvSignUp;
    private DatabaseReference databaseReference; // Firebase database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize input fields and button
        lem = findViewById(R.id.lem);
        lps = findViewById(R.id.lps);
        lbt = findViewById(R.id.lbt);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Set up login button click listener
        lbt.setOnClickListener(v -> loginUser());

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Login.this, Registration.class)); // Change to Login.java
//                finish();
                Intent i = new Intent(Login.this, ShoppingLIst.class);
                startActivity(i);
            }
        });
    }

    private void loginUser() {
        String email = lem.getText().toString().trim();
        String password = lps.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(email)) {
            lem.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            lps.setError("Password is required");
            return;
        }

        // Hash the password before comparing
        String hashedPassword = hashPassword(password);

        // Query the database for user matching the provided email
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        UserModel user = userSnapshot.getValue(UserModel.class);
                        if (user != null && user.password.equals(hashedPassword)) {
                            Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            // Redirect directly to MainActivity or another page
                            startActivity(new Intent(Login.this, ShoppingLIst.class));
                            finish();
                            return;
                        }
                    }
                    lps.setError("Incorrect password");
                    Toast.makeText(Login.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                } else {
                    lem.setError("User not found");
                    Toast.makeText(Login.this, "User not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Database error: " + error.getMessage());
            }
        });
    }

    // Method to hash the password using SHA-256 (not the most secure option for production)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password; // In case of failure, return plain password (not recommended for production)
        }
    }

    // Nested class for User model to be used in Firebase
    static class UserModel {
        public String name, email, password;

        public UserModel() {}

        public UserModel(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }
}