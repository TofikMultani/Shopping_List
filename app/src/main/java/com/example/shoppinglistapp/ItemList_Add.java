package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
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

public class ItemList_Add extends AppCompatActivity {
    ImageView ilsaveback,ilaccsave;
    Button ilsave;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_add);

        ilsaveback = findViewById(R.id.ilsaveback);
        ilaccsave = findViewById(R.id.ilaccsave);
        ilsave = findViewById(R.id.ilsave);

        ilsaveback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ItemList_Add.this,ItemList.class);
                startActivity(i);
            }
        });

        ilaccsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ItemList_Add.this,Account_Show.class);
                startActivity(i);
            }
        });

        ilsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ItemList_Add.this,ItemList.class);
                startActivity(i);
            }
        });

    }
}