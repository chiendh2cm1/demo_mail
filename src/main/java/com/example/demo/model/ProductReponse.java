package com.example.demo.model;

public class ProductReponse {
    private Long id;
    private String product_name;
    private double price;
    private String description;
    private long category_id;

    public ProductReponse(Long id, String product_name, double price, String description, long category_id) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.category_id = category_id;
    }

    public ProductReponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }
}
