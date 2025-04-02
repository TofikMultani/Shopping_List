package com.example.shoppinglistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {
    ArrayList<ShoppingItem> itemList;

    public ShoppingAdapter(ArrayList<ShoppingItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ShoppingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingItem item = itemList.get(position);
        holder.nameTextView.setText("Name: " + item.getName());
        holder.descTextView.setText("Description: " + item.getDescription());
        holder.dateTextView.setText("Date: " + item.getDate());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ShoppingViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, descTextView, dateTextView;
        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.row_name);
            descTextView = itemView.findViewById(R.id.row_desc);
            dateTextView = itemView.findViewById(R.id.row_date);
        }
    }
}
