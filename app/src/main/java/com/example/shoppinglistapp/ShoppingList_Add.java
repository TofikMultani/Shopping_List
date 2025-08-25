package com.example.shoppinglistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShoppingList_Add extends AppCompatActivity {

    EditText nameInput, descInput;
    Button saveBtn;
    String PREF_NAME;
    ImageView slbacksave;
    SharedPreferences sharedPreferences;
    ArrayList<shopping_item> itemList = new ArrayList<>();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_add);

        nameInput = findViewById(R.id.shoppingName);
        descInput = findViewById(R.id.description);
        saveBtn = findViewById(R.id.saveButton);
        slbacksave = findViewById(R.id.slbacksave);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getUid() != null) {
            PREF_NAME = "shopping_" + currentUser.getUid(); // ✅ Match this with ShoppingLIst.java
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            loadList(); // ✅ Load previous data before adding
        }

        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String desc = descInput.getText().toString();
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter item name", Toast.LENGTH_SHORT).show();
                return;
            }

            shopping_item item = new shopping_item(name, desc, date);
            itemList.add(item);
            saveList();

            Toast.makeText(this, "Item Saved", Toast.LENGTH_SHORT).show();

            // ✅ Step 4: Force reload main list
            Intent intent = new Intent(this, ShoppingLIst.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        slbacksave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShoppingList_Add.this,ShoppingLIst.class);
                startActivity(i);
            }
        });
    }

    private void loadList() {
        String json = sharedPreferences.getString(PREF_NAME, "[]");
        TypeToken<ArrayList<shopping_item>> token = new TypeToken<ArrayList<shopping_item>>() {};
        itemList = gson.fromJson(json, token.getType());
    }

    private void saveList() {
        String json = gson.toJson(itemList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_NAME, json); // ✅ Correct key usage
        editor.apply();
    }
}
