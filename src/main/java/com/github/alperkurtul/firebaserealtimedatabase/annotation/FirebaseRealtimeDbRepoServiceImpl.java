package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpUnauthorizedException;
import com.github.alperkurtul.useoffirebaserealtimedatabase.constants.SecretConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class FirebaseRealtimeDbRepoServiceImpl<FC, FD, ID> implements FirebaseRealtimeDbRepoService<FC, FD, ID> {
    // Type <FC> : Class for Firebase Connection info and Document Id info
    // Type <FD> : Class for Firebase Document

    @Autowired
    private RestTemplate restTemplate;

    private Class<FC> firebaseConnectionClazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<FD> firebaseDocumentClazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    private Class<ID> documentIdClazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    private String documentPath;
    private Field authKeyField;
    private Field documentIdField;

    public FirebaseRealtimeDbRepoServiceImpl() {

        if (firebaseDocumentClazz.isAnnotationPresent(FirebaseDocumentPath.class)) {
            documentPath = firebaseDocumentClazz.getAnnotation(FirebaseDocumentPath.class).value();
            if (!documentPath.isEmpty()) {
                if (!documentPath.substring(0,1).equals("/")) {
                    documentPath = "/" + documentPath;
                }
            } else {
                throw new RuntimeException("@FirebaseDocumentPath annotation's value is not set!");
            }
        } else {
            throw new RuntimeException("There is no @FirebaseDocumentPath annotation!");
        }

        this.documentIdField = (Field)Arrays.stream(this.firebaseConnectionClazz.getDeclaredFields()).filter((field) -> {
            return field.isAnnotationPresent(FirebaseDocumentId.class);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException("There is no @FirebaseDocumentId annotation!");
        });

        this.authKeyField = (Field)Arrays.stream(this.firebaseConnectionClazz.getDeclaredFields()).filter((field) -> {
            return field.isAnnotationPresent(FirebaseUserAuthKey.class);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException("There is no @FirebaseUserAuthKey annotation!");
        });

    }

    @Override
    public FD read(FC fc) {
        String authKey;
        String documentId;
        String methodName = "";

        try {
            methodName = "get" + this.documentIdField.getName().substring(0,1).toUpperCase() + this.documentIdField.getName().substring(1);
            Method method = fc.getClass().getMethod(methodName);
            documentId = (String)method.invoke(fc);

            methodName = "get" + this.authKeyField.getName().substring(0,1).toUpperCase() + this.authKeyField.getName().substring(1);
            method = fc.getClass().getMethod(methodName);
            authKey = (String)method.invoke(fc);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("There is no " + methodName + " method in Class " + fc.getClass().getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (documentId.isEmpty()) {
            throw new RuntimeException("@FirebaseDocumentId annotation's value is not set!");
        }
        if (authKey.isEmpty()) {
            throw new RuntimeException("@FirebaseUserAuthKey annotation's value is not set!");
        }

        String url = SecretConstants.FIREBASE_PROJECT_URL;
        url = url + this.documentPath + "/" + documentId + ".json?auth=" + authKey;

        ResponseEntity<FD> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, this.firebaseDocumentClazz);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new HttpBadRequestException(e.getResponseBodyAsString());
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new HttpNotFoundException(e.getResponseBodyAsString());
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new HttpUnauthorizedException(e.getResponseBodyAsString());
            } else {
                throw new RuntimeException();
            }
        }

        if (responseEntity.getBody() == null) {
            throw new HttpNotFoundException("FirebaseDocumentId Not Found");
        }

        return responseEntity.getBody();

    }

}
