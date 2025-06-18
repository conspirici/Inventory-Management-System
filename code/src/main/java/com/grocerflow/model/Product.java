package com.grocerflow.model;

import java.sql.Timestamp;

public class Product {
    private int productId;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private int addedBy;
    private Timestamp addedAt;

    public Product() {}

    public Product(int productId, String name, String category, int quantity,
                   double price, int addedBy, Timestamp addedAt) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.addedBy = addedBy;
        this.addedAt = addedAt;
    }

    public Product(String name, String category, int quantity, double price, int addedBy) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.addedBy = addedBy;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }
}
