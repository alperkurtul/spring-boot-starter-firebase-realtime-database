package com.github.alperkurtul.firebaserealtimedatabase.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseUserAuthKey;

import java.math.BigDecimal;

@FirebaseDocumentPath("/product")
public class Product {
    @FirebaseUserAuthKey
    private String authKey;
    @FirebaseDocumentId
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
