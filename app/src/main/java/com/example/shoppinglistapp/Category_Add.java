package com.example.shoppinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Category_Add extends AppCompatActivity {
    ImageView ctbacksave;
    Button ctsavebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        ctbacksave = findViewById(R.id.ctbacksave);
        ctsavebt = findViewById(R.id.ctsavebt);

        ctbacksave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Category_Add.this,Category.class);
                startActivity(i);
            }
        });

        ctsavebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Category_Add.this,Category.class);
                startActivity(i);
            }
        });
    }
}