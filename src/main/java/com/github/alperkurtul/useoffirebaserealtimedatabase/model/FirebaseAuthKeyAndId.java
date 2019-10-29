package com.github.alperkurtul.useoffirebaserealtimedatabase.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseAuthKey;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocument;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseId;

@FirebaseDocument("/product")
public class FirebaseAuthKeyAndId {
    @FirebaseAuthKey
    private String authKey;
    @FirebaseId
    private String firebaseId;

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

    public FirebaseAuthKeyAndId(String authKey, String firebaseId) {
        this.authKey = authKey;
        this.firebaseId = firebaseId;
    }

}
