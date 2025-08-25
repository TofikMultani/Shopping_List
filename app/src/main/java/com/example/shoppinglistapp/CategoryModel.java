package com.example.shoppinglistapp;

public class CategoryModel {
    private String id, name, desc;
    private String date;

    public CategoryModel(String id, String name, String desc,String date) {
        this.id = id;
        this.name = name;
        this.desc = desc;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
