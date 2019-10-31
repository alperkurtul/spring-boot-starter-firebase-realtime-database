package com.github.alperkurtul.useoffirebaserealtimedatabase.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseAuthKey;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocument;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseId;

import java.math.BigDecimal;

@FirebaseDocument("/product")
public class Product {
    @FirebaseAuthKey
    private String authKey;
    @FirebaseId
    private String firebaseId;

    private String id;
    private String name;
    private BigDecimal price;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

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
