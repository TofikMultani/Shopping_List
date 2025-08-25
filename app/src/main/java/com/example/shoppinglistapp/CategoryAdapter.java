/*
package com.example.shoppinglistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categoryList;
    private String sharedPreferences;
    private String prefKey;
    private String listKey;
    private Gson gson = new Gson();

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryList,
                           String sharedPreferences, String prefKey, String listKey) {
        this.context = context;
        this.categoryList = categoryList;
        this.sharedPreferences = sharedPreferences;
        this.prefKey = prefKey;
        this.listKey = listKey;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false); // updated layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel model = categoryList.get(position);
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDesc());
        holder.date.setText(model.getDate()); // bind date

        // Tap to open ItemList.java
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemList.class);
            intent.putExtra("categoryKey", model.getId());
            intent.putExtra("categoryName", model.getName());
            context.startActivity(intent);
        });

        // Long press to delete with undo
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Delete Category")
                    .setMessage("Are you sure you want to delete this category?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        CategoryModel deleted = categoryList.remove(position);
                        notifyItemRemoved(position);
                        saveList();

                        Toast.makeText(context, "Category deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });

    }

    private void saveList() {
        String json = gson.toJson(categoryList);
        sharedPreferences.edit().putString(listKey, json).apply();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            desc = itemView.findViewById(R.id.itemDescription);
            date = itemView.findViewById(R.id.itemDate); // added for date
        }
    }
}
*/


package com.example.shoppinglistapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categoryList;
    private SharedPreferences sharedPreferences; // Changed type to SharedPreferences
    private String prefKey;
    private String listKey;
    private Gson gson = new Gson();

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryList,
                           SharedPreferences sharedPreferences, String prefKey, String listKey) {
        this.context = context;
        this.categoryList = categoryList;
        this.sharedPreferences = sharedPreferences; // Use SharedPreferences directly
        this.prefKey = prefKey;
        this.listKey = listKey;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel model = categoryList.get(position);
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDesc());
        holder.date.setText(model.getDate());

        // Tap to open ItemList.java
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemList.class);
            intent.putExtra("categoryKey", model.getId());
            intent.putExtra("categoryName", model.getName());
            context.startActivity(intent);
        });

        // Long press to delete with undo
        holder.itemView.setOnLongClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Delete Category")
                    .setMessage("Are you sure you want to delete this category?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        CategoryModel deleted = categoryList.remove(position);
                        notifyItemRemoved(position);
                        saveList();
                        Toast.makeText(context, "Category deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    private void saveList() {
        String json = gson.toJson(categoryList);
        sharedPreferences.edit().putString(listKey, json).apply(); // Corrected to use SharedPreferences
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            desc = itemView.findViewById(R.id.itemDescription);
            date = itemView.findViewById(R.id.itemDate);
        }
    }
}