package com.example.shoppinglistapp;

public class CategoryItem {
    private String name;
    private String description;

    public CategoryItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
