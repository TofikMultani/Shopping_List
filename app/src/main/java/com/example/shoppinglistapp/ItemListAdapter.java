package com.example.shoppinglistapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<ItemModel> itemList;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String categoryKey;
    private Gson gson = new Gson();

    public ItemListAdapter(List<ItemModel> itemList, Context context, SharedPreferences sharedPreferences, String categoryKey) {
        this.itemList = itemList;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.categoryKey = categoryKey;
    }

    public ItemListAdapter(List<ItemModel> itemList, ItemList context) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDescription());
        holder.checkBox.setChecked(item.isCollected());

        updateStrikeThrough(holder, item.isCollected());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setCollected(isChecked);
            updateStrikeThrough(holder, isChecked);
            saveItemList();
        });

        // Optional: Long click to delete
        holder.itemView.setOnLongClickListener(v -> {
            itemList.remove(position);
            notifyItemRemoved(position);
            saveItemList();
            return true;
        });
    }

    private void updateStrikeThrough(ViewHolder holder, boolean isChecked) {
        if (isChecked) {
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.name.setPaintFlags(holder.name.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void saveItemList() {
        String key = "Items_" + categoryKey;
        String json = gson.toJson(itemList);
        sharedPreferences.edit().putString(key, json).apply();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            desc = itemView.findViewById(R.id.itemDescription);
            checkBox = itemView.findViewById(R.id.itemCheckbox);
        }
    }
}
