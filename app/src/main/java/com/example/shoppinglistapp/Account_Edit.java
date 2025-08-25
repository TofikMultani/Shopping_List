/*
package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account_Edit extends AppCompatActivity {

    EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    Button saveButton;
    ImageView backBtn;

    FirebaseAuth auth;
    DatabaseReference databaseRef;
    FirebaseUser currentUser;

    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        fullNameEditText = findViewById(R.id.nameEdit);
        emailEditText = findViewById(R.id.emailEdit);
        passwordEditText = findViewById(R.id.passwordEdit);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEdit);
        saveButton = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backButton);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        String uid = currentUser.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        // Prefill email field
        emailEditText.setText(currentUser.getEmail());

        backBtn.setOnClickListener(v -> finish());

        saveButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isEmpty(password) && !password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog = ProgressDialog.show(Account_Edit.this, "Updating...", "Please wait...", true);

            // Step 1: Update name in Firebase Realtime DB
            databaseRef.child("name").setValue(fullName).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    // Step 2: If email changed, update it
                    if (!email.equals(currentUser.getEmail())) {
                        currentUser.updateEmail(email).addOnCompleteListener(emailTask -> {
                            if (emailTask.isSuccessful()) {
                                // Step 3: If password is entered, update it
                                updatePasswordIfNeeded(password);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(this, "Email update failed: " + emailTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Email not changed, check password
                        updatePasswordIfNeeded(password);
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Name update failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void updatePasswordIfNeeded(String password) {
        if (!TextUtils.isEmpty(password)) {
            currentUser.updatePassword(password).addOnCompleteListener(passTask -> {
                progressDialog.dismiss();
                if (passTask.isSuccessful()) {
                    Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Account_Show.class));
                    finish();
                } else {
                    Toast.makeText(this, "Password update failed: " + passTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Account_Show.class));
            finish();
        }
    }
}
*/

package com.example.shoppinglistapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account_Edit extends AppCompatActivity {

    EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    Button saveButton;
    ImageView backBtn;

    FirebaseAuth auth;
    DatabaseReference databaseRef;
    FirebaseUser currentUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);

        fullNameEditText = findViewById(R.id.nameEdit);
        emailEditText = findViewById(R.id.emailEdit);
        passwordEditText = findViewById(R.id.passwordEdit);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEdit);
        saveButton = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backButton);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        String uid = currentUser.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        emailEditText.setText(currentUser.getEmail());
        fullNameEditText.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "");

        backBtn.setOnClickListener(v -> finish());

        saveButton.setOnClickListener(v -> updateUserDetails());
    }

    private void updateUserDetails() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(password) && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = ProgressDialog.show(this, "Updating...", "Please wait...", true);

        new android.os.Handler().postDelayed(() -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(this, "Update taking too long. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }, 15000);

        databaseRef.child("name").setValue(fullName).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("DEBUG", "Name updated successfully!");
                updateEmail(email, password);
            } else {
                progressDialog.dismiss();
                Log.e("DEBUG", "Name update failed: " + task.getException().getMessage());
                Toast.makeText(this, "Name update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmail(String email, String password) {
        if (!email.equals(currentUser.getEmail())) {
            currentUser.updateEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("DEBUG", "Email updated successfully!");
                    updatePassword(password);
                } else {
                    progressDialog.dismiss();
                    Log.e("DEBUG", "Email update failed: " + task.getException().getMessage());
                    Toast.makeText(this, "Email update failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            updatePassword(password);
        }
    }

    private void updatePassword(String password) {
        if (!TextUtils.isEmpty(password)) {
            currentUser.updatePassword(password).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Log.d("DEBUG", "Password updated successfully!");
                    Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Account_Show.class));
                    finish();
                } else {
                    Log.e("DEBUG", "Password update failed: " + task.getException().getMessage());
                    Toast.makeText(this, "Password update failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Account_Show.class));
            finish();
        }
    }
}