package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseRealtimeDatabaseConfigurationProperties;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpUnauthorizedException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class FirebaseRealtimeDbRepoServiceImpl<T, ID> implements FirebaseRealtimeDbRepoService<T, ID> {
    /*
    Firebase Docs
    Firebase Database Rest API
    https://firebase.google.com/docs/reference/rest/database
    */

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper firebaseObjectMapper;

    @Autowired
    private FirebaseRealtimeDatabaseConfigurationProperties firebaseRealtimeDatabaseConfigurationProperties;

    private Class<T> firebaseDocumentClazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<ID> documentIdClazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
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

        this.documentIdField = (Field)Arrays.stream(this.firebaseDocumentClazz.getDeclaredFields()).filter((field) -> {
            return field.isAnnotationPresent(FirebaseDocumentId.class);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException("There is no @FirebaseDocumentId annotation!");
        });

        this.authKeyField = (Field)Arrays.stream(this.firebaseDocumentClazz.getDeclaredFields()).filter((field) -> {
            return field.isAnnotationPresent(FirebaseUserAuthKey.class);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException("There is no @FirebaseUserAuthKey annotation!");
        });

    }

    @Override
    public T read(T obj) {
        /*
        Firebase Database REST API
        GET - Reading Data
        */

        String authKey = getAnnotatedFielValue(obj, this.authKeyField);
        String firebaseId = getAnnotatedFielValue(obj, this.documentIdField);

        String url = generateUrl("read", authKey, firebaseId);
        ResponseEntity<T> responseEntity = null;
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

    @Deprecated  // Use 'saveWithRandomId' instead
    @Override
    public FirebaseSaveResponse save(T obj) {
        /*
        Firebase Database REST API
        POST - Pushing Data
        */

        return saveWithRandomId(obj);

    }

    @Override
    public FirebaseSaveResponse saveWithRandomId(T obj) {
        /*
        Firebase Database REST API
        POST - Pushing Data
        */

        String authKey = getAnnotatedFielValue(obj, this.authKeyField);
        String firebaseId = getAnnotatedFielValue(obj, this.documentIdField);

        String url = generateUrl("saveWithRandomId", authKey, firebaseId);

        HttpEntity<String> requestBody = null;
        try {
            requestBody = new HttpEntity(firebaseObjectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<FirebaseSaveResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, requestBody, FirebaseSaveResponse.class);
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

        return responseEntity.getBody();

    }

    @Override
    public FirebaseSaveResponse saveWithSpecificId(T obj) {
        /*
        Firebase Database REST API
        PUT - Writing Data
        */

        String authKey = getAnnotatedFielValue(obj, this.authKeyField);
        String firebaseId = getAnnotatedFielValue(obj, this.documentIdField);

        // Checking the data if it exists or not
        String url = "";
        try {
            read(obj);
        } catch (HttpNotFoundException e) {
            url = generateUrl("saveWithSpecificId", authKey, firebaseId);
        }

        // If it exists, then throw error
        if (url.isEmpty()) {
            throw new HttpBadRequestException("FirebaseDocumentId Already Exists");
        }

        HttpEntity<String> requestBody = null;
        try {
            requestBody = new HttpEntity(firebaseObjectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            restTemplate.put(url, requestBody);
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

        FirebaseSaveResponse firebaseSaveResponse = new FirebaseSaveResponse();
        firebaseSaveResponse.setName(firebaseId);

        return firebaseSaveResponse;

    }

    @Override
    public void update(T obj) {
        /*
        Firebase Database REST API
        PUT - Writing Data
        */

        String authKey = getAnnotatedFielValue(obj, this.authKeyField);
        String firebaseId = getAnnotatedFielValue(obj, this.documentIdField);

        // Checking the data if it exists or not
        read(obj);

        // Updating data
        String url = generateUrl("update", authKey, firebaseId);

        HttpEntity<String> requestBody = null;
        try {
            requestBody = new HttpEntity(firebaseObjectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            restTemplate.put(url, requestBody);
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

    }

    @Override
    public void delete(T obj) {
        /*
        Firebase Database REST API
        DELETE - Removing Data
        */

        String authKey = getAnnotatedFielValue(obj, this.authKeyField);
        String firebaseId = getAnnotatedFielValue(obj, this.documentIdField);

        // Checking the data if it exists or not
        read(obj);

        // Deleting data
        String url = generateUrl("delete", authKey, firebaseId);
        try {
            restTemplate.delete(url);
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

    }

    private String getAnnotatedFielValue(T obj, Field field) {
        String fieldValue;
        String methodName = "";

        try {
            methodName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
            Method method = obj.getClass().getMethod(methodName);
            fieldValue = (String)method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("There is no " + methodName + " method in Class " + obj.getClass().getName());
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

        return fieldValue;

    }

    private String generateUrl( String callerMethod, String authKey, String firebaseId) {

        if (callerMethod.equals("read") || callerMethod.equals("update") || callerMethod.equals("delete")) {
            if (firebaseId.isEmpty()) {
                throw new RuntimeException("@FirebaseDocumentId annotation's value is not set!");
            }
        }

        if (authKey.isEmpty()) {
            throw new RuntimeException("@FirebaseUserAuthKey annotation's value is not set!");
        }

        String url = firebaseRealtimeDatabaseConfigurationProperties.getDatabaseUrl();

        if (callerMethod.equals("read") || callerMethod.equals("update") || callerMethod.equals("delete")) {
            url = url + this.documentPath + "/" + firebaseId + ".json?auth=" + authKey;
        } else if (callerMethod.equals("saveWithRandomId")) {
            url = url + this.documentPath + ".json?auth=" + authKey;
        } else if (callerMethod.equals("saveWithSpecificId")) {
            url = url + this.documentPath + "/" + firebaseId + ".json?auth=" + authKey;
        }

        return url;

    }

}
