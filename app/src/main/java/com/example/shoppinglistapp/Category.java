package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Category extends AppCompatActivity {

    Button ctbt;
    ImageView ctback,ctacc;
    LinearLayout ctitem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        ctbt = findViewById(R.id.ctbt);
        ctback = findViewById(R.id.ctback);
        ctacc = findViewById(R.id.ctacc);

        ctitem = findViewById(R.id.ctitem);

        ctbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Category_Add.class);
                startActivity(i);
            }
        });

        ctback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, ShoppingLIst.class);
                startActivity(i);
            }
        });

        ctacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, Account_Show.class);
                startActivity(i);
            }
        });


        ctitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Category.this, ItemList.class);
                startActivity(i);
            }
        });
    }
}