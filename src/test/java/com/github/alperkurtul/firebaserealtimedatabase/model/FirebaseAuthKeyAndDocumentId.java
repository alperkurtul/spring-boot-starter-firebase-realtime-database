package com.github.alperkurtul.firebaserealtimedatabase.model;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseUserAuthKey;

public class FirebaseAuthKeyAndDocumentId {
    @FirebaseUserAuthKey
    private String authKey;
    @FirebaseDocumentId
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

    public FirebaseAuthKeyAndDocumentId(String authKey, String firebaseId) {
        this.authKey = authKey;
        this.firebaseId = firebaseId;
    }

}
