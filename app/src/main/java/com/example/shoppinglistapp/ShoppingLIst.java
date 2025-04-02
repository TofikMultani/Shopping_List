package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ShoppingLIst extends AppCompatActivity {
    public static ArrayList<ShoppingItem> itemList = new ArrayList<>();
    RecyclerView shoppingRecyclerView;
    ShoppingAdapter adapter;
    Button slnlbt;
    ImageView slacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingRecyclerView = findViewById(R.id.shoppingRecyclerView);
        slnlbt = findViewById(R.id.slnlbt);
        slacc = findViewById(R.id.slacc);

        // Setup RecyclerView
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShoppingAdapter(itemList);
        shoppingRecyclerView.setAdapter(adapter);

        // Receive data from Add page
        if (getIntent().hasExtra("name")) {
            String name = getIntent().getStringExtra("name");
            String desc = getIntent().getStringExtra("desc");
            String date = getIntent().getStringExtra("date");
            itemList.add(new ShoppingItem(name, desc, date));
            adapter.notifyDataSetChanged();
        }

        // Button for New List
        slnlbt.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingLIst.this, ShoppingList_Add.class);
            startActivity(i);
        });

        // Account Click (optional)
        slacc.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingLIst.this, Account_Show.class);
            startActivity(i);
        });
    }
}
