package com.example.shoppinglistapp;

public class shopping_item {
    private String itemName;
    private String description;
    private String date;

    public shopping_item(String itemName, String description, String date) {
        this.itemName = itemName;
        this.description = description;
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
