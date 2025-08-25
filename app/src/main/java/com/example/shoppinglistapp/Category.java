/*
package com.example.shoppinglistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class Category extends AppCompatActivity {

    EditText inputCategoryName, inputCategoryDesc,date;
    Button addCategoryBtn;
    RecyclerView categoryRecyclerView;
    ImageView ctacc,ctback;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    CategoryAdapter adapter;
    SharedPreferences sharedPreferences;
    Gson gson;
    String itemKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        inputCategoryName = findViewById(R.id.inputCategoryName);
        inputCategoryDesc = findViewById(R.id.inputCategoryDesc);
        addCategoryBtn = findViewById(R.id.addCategoryBtn);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        ctacc = findViewById(R.id.ctacc);
        ctback = findViewById(R.id.ctback);
// 👇 Current date format ma convert karo
        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        //CategoryModel category = new CategoryModel(name, description, currentDate);

        itemKey = getIntent().getStringExtra("itemKey");
        sharedPreferences = getSharedPreferences("Categories_" + itemKey, Context.MODE_PRIVATE);
        gson = new Gson();

        loadCategories();

        adapter = new CategoryAdapter(
                Category.this,
                categoryList,
                sharedPreferences.toString(),
                "Categories_" + itemKey,
                "list"
        );
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(Category.this));
        categoryRecyclerView.setAdapter(adapter);

        addCategoryBtn.setOnClickListener(v -> {
            String name = inputCategoryName.getText().toString().trim();
            String desc = inputCategoryDesc.getText().toString().trim();

            if (!name.isEmpty()) {
                String id = UUID.randomUUID().toString();
                CategoryModel newCat = new CategoryModel(id, name, desc,currentDate );
                categoryList.add(newCat);
                adapter.notifyItemInserted(categoryList.size() - 1);
                saveCategories();

                inputCategoryName.setText("");
                inputCategoryDesc.setText("");
            } else {
                Toast.makeText(Category.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });

        ctacc.setOnClickListener(view -> {
            Intent i = new Intent(Category.this, Account_Show.class);
            startActivity(i);
        });

        ctback.setOnClickListener(view -> {
            Intent i = new Intent(Category.this, ShoppingLIst.class);
            startActivity(i);
        });
    }

    private void saveCategories() {
        String json = gson.toJson(categoryList);
        sharedPreferences.edit().putString("list", json).apply();
    }

    private void loadCategories() {
        String json = sharedPreferences.getString("list", null);
        Type type = new TypeToken<ArrayList<CategoryModel>>() {}.getType();
        ArrayList<CategoryModel> savedList = gson.fromJson(json, type);
        if (savedList != null) {
            categoryList.addAll(savedList);
        }
    }
}





*/

package com.example.shoppinglistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Category extends AppCompatActivity {

    EditText inputCategoryName, inputCategoryDesc;
    Button addCategoryBtn;
    RecyclerView categoryRecyclerView;
    ImageView ctacc, ctback;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    CategoryAdapter adapter;
    SharedPreferences sharedPreferences;
    Gson gson;
    String itemKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        inputCategoryName = findViewById(R.id.inputCategoryName);
        inputCategoryDesc = findViewById(R.id.inputCategoryDesc);
        addCategoryBtn = findViewById(R.id.addCategoryBtn);
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        ctacc = findViewById(R.id.ctacc);
        ctback = findViewById(R.id.ctback);

        // Current date format
        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        itemKey = getIntent().getStringExtra("itemKey");
        sharedPreferences = getSharedPreferences("Categories_" + itemKey, Context.MODE_PRIVATE);
        gson = new Gson();

        loadCategories();

        adapter = new CategoryAdapter(
                this,
                categoryList,
                sharedPreferences, // Pass SharedPreferences object directly
                "Categories_" + itemKey,
                "list"
        );

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(adapter);

        addCategoryBtn.setOnClickListener(v -> {
            String name = inputCategoryName.getText().toString().trim();
            String desc = inputCategoryDesc.getText().toString().trim();

            if (!name.isEmpty()) {
                String id = UUID.randomUUID().toString();
                CategoryModel newCat = new CategoryModel(id, name, desc, currentDate);
                categoryList.add(newCat);
                adapter.notifyItemInserted(categoryList.size() - 1);
                saveCategories();

                inputCategoryName.setText("");
                inputCategoryDesc.setText("");
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            }
        });

        ctacc.setOnClickListener(view -> {
            Intent i = new Intent(this, Account_Show.class);
            startActivity(i);
        });

        ctback.setOnClickListener(view -> {
            Intent i = new Intent(this, ShoppingLIst.class);
            startActivity(i);
        });
    }

    private void saveCategories() {
        String json = gson.toJson(categoryList);
        sharedPreferences.edit().putString("list", json).apply();
    }

    private void loadCategories() {
        String json = sharedPreferences.getString("list", null);
        Type type = new TypeToken<ArrayList<CategoryModel>>() {}.getType();
        ArrayList<CategoryModel> savedList = gson.fromJson(json, type);
        if (savedList != null) {
            categoryList.addAll(savedList);
        }
    }
}
