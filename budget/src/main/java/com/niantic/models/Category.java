package com.niantic.models;

public class Category {
    private int categoryId;
    private String categoryName;
    private String description;

    public Category() {
    }

    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.categoryName = name;
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
