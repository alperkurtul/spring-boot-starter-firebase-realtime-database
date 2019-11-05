package com.github.alperkurtul.firebaserealtimedatabase.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;

import java.math.BigDecimal;

@FirebaseDocumentPath("/product")
public class Product {
    private String id;
    private String name;
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
