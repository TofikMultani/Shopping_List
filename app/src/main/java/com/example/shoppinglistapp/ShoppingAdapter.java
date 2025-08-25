package com.example.shoppinglistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    Context context;
    ArrayList<shopping_item> itemList;
    SharedPreferences sharedPreferences;
    String prefName;

    public ShoppingAdapter(Context context, ArrayList<shopping_item> itemList, SharedPreferences sharedPreferences, String prefName) {
        this.context = context;
        this.itemList = itemList;
        this.sharedPreferences = sharedPreferences;
        this.prefName = prefName;
    }

    @NonNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder holder, int position) {
        shopping_item item = itemList.get(position);

        holder.nameTextView.setText(item.getItemName());
        holder.descTextView.setText(item.getDescription());
        holder.dateTextView.setText(item.getDate());



        // Open category page on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Category.class);
            intent.putExtra("itemKey", item.getItemName());
            context.startActivity(intent);
        });

        // Delete with undo on long press
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete?")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        shopping_item deletedItem = itemList.get(position);
                        int deletedPosition = position;

                        itemList.remove(position);
                        notifyItemRemoved(position);
                        saveList();

                        Snackbar.make(v, "Item deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", view -> {
                                    itemList.add(deletedPosition, deletedItem);
                                    notifyItemInserted(deletedPosition);
                                    saveList();
                                }).show();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void saveList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(itemList);
        editor.putString(prefName, json);
        editor.apply();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descTextView, dateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.itemName);
            descTextView = itemView.findViewById(R.id.itemDescription);
            dateTextView = itemView.findViewById(R.id.itemDate);
        }
    }
}
