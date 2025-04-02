package com.example.shoppinglistapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ShoppingList_Add extends AppCompatActivity {

    Button slsave;
    ImageView slbacksave;
    EditText dateEditText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_add);

        dateEditText = findViewById(R.id.dateEditText);
        slsave = findViewById(R.id.slsave);
        slbacksave =findViewById(R.id.slbacksave);

        slsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = findViewById(R.id.nameEditText);
                EditText descEditText = findViewById(R.id.descEditText);
                EditText dateEditText = findViewById(R.id.dateEditText);

                String name = nameEditText.getText().toString();
                String desc = descEditText.getText().toString();
                String date = dateEditText.getText().toString();

                Intent i = new Intent(ShoppingList_Add.this, ShoppingLIst.class);
                i.putExtra("name", name);
                i.putExtra("desc", desc);
                i.putExtra("date", date);
                startActivity(i);
            }
        });

        slbacksave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShoppingList_Add.this, ShoppingLIst.class);
                startActivity(i);
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ShoppingList_Add.this,
                        (_view, year1, month1, dayOfMonth) -> {String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;dateEditText.setText(selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }
}