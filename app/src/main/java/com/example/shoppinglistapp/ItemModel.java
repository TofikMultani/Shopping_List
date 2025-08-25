//package com.example.shoppinglistapp;
//
//public class ItemModel {
//    private String name;
//    private String description;
//    private boolean collected;
//
//    public ItemModel(String name, String description, boolean collected) {
//        this.name = name;
//        this.description = description;
//        this.collected = collected;
//    }
//
//    public ItemModel(String milk, String dairyProduct) {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public boolean isCollected() {
//        return collected;
//    }
//
//    public void setCollected(boolean collected) {
//        this.collected = collected;
//    }
//}


package com.example.shoppinglistapp;

public class ItemModel {
    private String name;
    private String description;
    private boolean collected;

    public ItemModel(String name, String description, boolean collected) {
        this.name = name;
        this.description = description;
        this.collected = collected;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}

