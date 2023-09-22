package com.example.demo1.model;

import java.math.BigDecimal;

public class Product implements Comparable<Product>{
    //
    private int id;

    private String name;

    private int price;

    private String description;

    private Category category;

    public Product( String name, int price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public int compareTo(Product product) {
        return Integer.compare(this.id, product.getId());
    }
}