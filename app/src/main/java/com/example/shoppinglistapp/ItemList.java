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

public class ItemList extends AppCompatActivity {

     ImageView ilbcak,ilacc,iledit;
     Button ilbt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ilbcak = findViewById(R.id.ilbcak);
        ilacc = findViewById(R.id.ilacc);
        ilbt = findViewById(R.id.ilbt);


        ilbcak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemList.this,Category.class);
                startActivity(i);
            }
        });


        ilacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ItemList.this,Account_Show.class);
                startActivity(i);
            }
        });

        ilbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ItemList.this, ItemList_Add.class);
                startActivity(i);
            }
        });


    }
}