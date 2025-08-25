package com.example.shoppinglistapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText forgotEmail;
    Button resetBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotEmail = findViewById(R.id.forgotEmail);
        resetBtn = findViewById(R.id.resetBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(v -> {
            String email = forgotEmail.getText().toString();

            if (email.isEmpty()) {
                forgotEmail.setError("Email required");
                forgotEmail.requestFocus();
                return;
            }

            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Reset link sent to your email", Toast.LENGTH_LONG).show();
                            finish(); // Go back to login
                        } else {
                            Toast.makeText(ForgotPassword.this, "Failed to send reset email", Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}