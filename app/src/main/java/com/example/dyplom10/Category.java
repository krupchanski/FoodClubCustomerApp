package com.example.dyplom10;

public class Category {
    private String name;
    private String icon;

    public Category() {}

    public Category(Category other) {
        this.name = other.name;
        this.icon = other.icon;
    }

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) { this.icon = icon; }
}
