package com.example.shoppinglistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ShoppingLIst extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addButton;
    ImageView slacc;
    EditText searchEditText;
    ArrayList<shopping_item> itemList = new ArrayList<>();
    ArrayList<shopping_item> filteredList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    String PREF_NAME;
    ShoppingAdapter adapter;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);
        slacc = findViewById(R.id.slacc);
        searchEditText = findViewById(R.id.searchEditText);

        // Firebase current user check
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            PREF_NAME = "shopping_" + currentUser.getUid();
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            loadShoppingList();
        }

        // Set up RecyclerView adapter
        filteredList.addAll(itemList); // Initially show all items
        adapter = new ShoppingAdapter(this, filteredList, sharedPreferences, PREF_NAME);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Add Item Button
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingList_Add.class);
            startActivity(intent);
        });

        // Navigate to Profile/Account
        slacc.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingLIst.this, Account_Show.class);
            startActivity(intent);
        });

        // Search functionality
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void loadShoppingList() {
        String json = sharedPreferences.getString(PREF_NAME, "[]");
        TypeToken<ArrayList<shopping_item>> token = new TypeToken<ArrayList<shopping_item>>() {};
        itemList = new Gson().fromJson(json, token.getType());
    }

    private void filterItems(String text) {
        filteredList.clear();
        for (shopping_item item : itemList) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        // Double back press to exit
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity(); // Exit app completely
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}

