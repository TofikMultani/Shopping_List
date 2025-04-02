package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Registration extends AppCompatActivity {

    private EditText rnm, rem, rps, rcps; // Input fields for registration
    private Button btnSignUp; // Button for sign-up action
    private TextView tvSignin; // Text for redirecting to sign-in
    private DatabaseReference databaseReference; // Firebase database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize input fields and button
        rnm = findViewById(R.id.rnm);
        rem = findViewById(R.id.rem);
        rps = findViewById(R.id.rps);
        rcps = findViewById(R.id.rcps);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignin = findViewById(R.id.tvSignin);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Handle Sign-Up button click
        btnSignUp.setOnClickListener(v -> registerUser());

        // Navigate to Login page on Sign-In text click
        tvSignin.setOnClickListener(v -> {
            startActivity(new Intent(Registration.this, Login.class)); // Change to Login.java
            finish();
        });
    }

    private void registerUser() {
        String name = rnm.getText().toString().trim();
        String email = rem.getText().toString().trim();
        String password = rps.getText().toString().trim();
        String confirmPassword = rcps.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(name)) {
            rnm.setError("Full Name is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            rem.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            rps.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            rcps.setError("Passwords do not match");
            return;
        }

        // Hash the password for security
        String hashedPassword = hashPassword(password);

        // Check if email is already registered
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    rem.setError("Email already registered");
                    Toast.makeText(Registration.this, "User with this email already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user details in Firebase Realtime Database
                    String userId = databaseReference.push().getKey();
                    UserModel user = new UserModel(name, email, hashedPassword);
                    databaseReference.child(userId).setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(Registration.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, Login.class));
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(Registration.this, "Failed to register!", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Database error: " + error.getMessage());
            }
        });
    }

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
            return password; // Fallback (not recommended for production)
        }
    }

    // User model class for Firebase data
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