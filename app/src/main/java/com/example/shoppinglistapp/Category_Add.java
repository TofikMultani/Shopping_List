package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Category_Add extends AppCompatActivity {

    EditText nameEditText, descEditText;
    Button saveButton;

    SharedPreferences sharedPreferences;
    String prefKey;
    String itemKey;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        nameEditText = findViewById(R.id.nameEditText);
        descEditText = findViewById(R.id.descEditText);
        saveButton = findViewById(R.id.saveButton);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        prefKey = "shopping_" + uid;
        itemKey = getIntent().getStringExtra("item_key");

        sharedPreferences = getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        loadCategories();

        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String desc = descEditText.getText().toString().trim();

            if (name.isEmpty()) {
                nameEditText.setError("Name required");
                return;
            }

            // ✅ Add this code here:
            String id = UUID.randomUUID().toString();
            //CategoryModel category = new CategoryModel(id, name, desc);
            //String id = UUID.randomUUID().toString();
            String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
            CategoryModel category = new CategoryModel(id, name, desc, currentDate); // pass date to model


            // Save to SharedPreferences
            categoryList.add(category);
            String json = new Gson().toJson(categoryList);
            sharedPreferences.edit().putString(itemKey, json).apply();

            // Save to Firestore
            FirebaseFirestore.getInstance()
                    .collection(prefKey)
                    .document(itemKey)
                    .collection("categories")
                    .document(id)
                    .set(category);

            Toast.makeText(this, "Category Added", Toast.LENGTH_SHORT).show();
            finish(); // go back to Category.java
        });
    }

    private void loadCategories() {
        String json = sharedPreferences.getString(itemKey, "[]");
        categoryList = new Gson().fromJson(json, new TypeToken<ArrayList<CategoryModel>>(){}.getType());
    }
}
