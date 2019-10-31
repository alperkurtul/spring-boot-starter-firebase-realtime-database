package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpBadRequestException;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpUnauthorizedException;
import com.github.alperkurtul.useoffirebaserealtimedatabase.constants.SecretConstants;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public class FirebaseRealtimeDbRepoServiceImpl<T, ID> implements FirebaseRealtimeDbRepoService<T, ID> {

    @Autowired
    private RestTemplate restTemplate;

    private Class<T> documentClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<ID> documentIdClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    private Field documentId;
    private String documentPath;
    private String firebaseAuthKey;

    private Class<T> clazz = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private String documentName = "";
    private String authKey = "";
    private String firebaseId = "";


    public FirebaseRealtimeDbRepoServiceImpl() {

        if (clazz.isAnnotationPresent(FirebaseDocument.class)) {
            documentName = clazz.getAnnotation(FirebaseDocument.class).value();
            if (!documentName.isEmpty()) {
                if (!documentName.substring(0,1).equals("/")) {
                    documentName = "/" + documentName;
                }
            } else {
                try {
                    throw new Exception("@FirebaseDocument annotation's value is not set!");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        } else {
            try {
                throw new Exception("There is no @FirebaseDocument annotation!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }


        this.documentId = (Field)Arrays.stream(this.documentClass.getDeclaredFields()).filter((field) -> {
            return field.isAnnotationPresent(FirebaseAuthKey.class);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException();
        });

        Boolean existFirebaseAuthKeyAnnotation = false;
        Boolean existFirebaseIdAnnotation = false;
        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(FirebaseAuthKey.class)) {
                if (existFirebaseAuthKeyAnnotation) {
                    try {
                        throw new Exception("@FirebaseAuthKey annotation must be used only once!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                } else {
                    existFirebaseAuthKeyAnnotation = true;
                    try {
                        field.setAccessible(true);
                        authKey = field.get(clazz).toString();
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
            }

            if (field.isAnnotationPresent(FirebaseId.class)) {
                if (existFirebaseIdAnnotation) {
                    try {
                        throw new Exception("@FirebaseId annotation must be used only once!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                } else {
                    existFirebaseIdAnnotation = true;
                    try {
                        field.setAccessible(true);
                        firebaseId = field.get(clazz).toString();
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
            }

        }

        if (!existFirebaseAuthKeyAnnotation) {
            try {
                throw new Exception("There is no @FirebaseAuthKey annotation!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        if (authKey.isEmpty()) {
            try {
                throw new Exception("You have to set the Authentication Key!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

        if (!existFirebaseAuthKeyAnnotation) {
            try {
                throw new Exception("There is no @FirebaseId annotation!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        if (firebaseId.isEmpty()) {
            try {
                throw new Exception("You have to set the Id!");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

    }

    @Override
    public T read(Object obj1) {
//        String documentName = "";
//        String authKey = "";
//        String firebaseId = "";
//
//        Class<T> clazz = (Class) obj1.getClass();  // veya Class<?> clazz = obj.getClass();
//        if (clazz.isAnnotationPresent(FirebaseDocument.class)) {
//            documentName = clazz.getAnnotation(FirebaseDocument.class).value();
//            if (!documentName.isEmpty()) {
//                if (!documentName.substring(0,1).equals("/")) {
//                    documentName = "/" + documentName;
//                }
//            } else {
//                try {
//                    throw new Exception("@FirebaseDocument annotation's value is not set!");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException();
//                }
//            }
//        } else {
//            try {
//                throw new Exception("There is no @FirebaseDocument annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//
//        Boolean existFirebaseAuthKeyAnnotation = false;
//        Boolean existFirebaseIdAnnotation = false;
//        for (Field field : clazz.getDeclaredFields()) {
//
//            if (field.isAnnotationPresent(FirebaseAuthKey.class)) {
//                if (existFirebaseAuthKeyAnnotation) {
//                    try {
//                        throw new Exception("@FirebaseAuthKey annotation must be used only once!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                } else {
//                    existFirebaseAuthKeyAnnotation = true;
//                    try {
//                        field.setAccessible(true);
//                        authKey = field.get(obj1).toString();
//                        field.setAccessible(false);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                }
//            }
//
//            if (field.isAnnotationPresent(FirebaseId.class)) {
//                if (existFirebaseIdAnnotation) {
//                    try {
//                        throw new Exception("@FirebaseId annotation must be used only once!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                } else {
//                    existFirebaseIdAnnotation = true;
//                    try {
//                        field.setAccessible(true);
//                        firebaseId = field.get(obj1).toString();
//                        field.setAccessible(false);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                }
//            }
//
//        }
//
//        if (!existFirebaseAuthKeyAnnotation) {
//            try {
//                throw new Exception("There is no @FirebaseAuthKey annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//        if (authKey.isEmpty()) {
//            try {
//                throw new Exception("You have to set the Authentication Key!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//
//        if (!existFirebaseAuthKeyAnnotation) {
//            try {
//                throw new Exception("There is no @FirebaseId annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//        if (firebaseId.isEmpty()) {
//            try {
//                throw new Exception("You have to set the Id!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }

        String url = SecretConstants.FIREBASE_PROJECT_URL;
        url = url + documentName + "/" + firebaseId + ".json?auth=" + authKey;

        Class<T> documentClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, documentClass);
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
            throw new HttpNotFoundException("FirebaseId Not Found");
        }

        return responseEntity.getBody();

    }


//    @Override
//    public DOC read(Object obj1, Object obj2) {
//        String documentName = "";
//        String authKey = "";
//        String firebaseId = "";
//
//        Class<AUTH> clazz = (Class) obj1.getClass();  // veya Class<?> clazz = obj.getClass();
//        if (clazz.isAnnotationPresent(FirebaseDocument.class)) {
//            documentName = clazz.getAnnotation(FirebaseDocument.class).value();
//            if (!documentName.isEmpty()) {
//                if (!documentName.substring(0,1).equals("/")) {
//                    documentName = "/" + documentName;
//                }
//            } else {
//                try {
//                    throw new Exception("@FirebaseDocument annotation's value is not set!");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException();
//                }
//            }
//        } else {
//            try {
//                throw new Exception("There is no @FirebaseDocument annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//
//         Boolean existFirebaseAuthKeyAnnotation = false;
//         Boolean existFirebaseIdAnnotation = false;
//        for (Field field : clazz.getDeclaredFields()) {
//
//            if (field.isAnnotationPresent(FirebaseAuthKey.class)) {
//                if (existFirebaseAuthKeyAnnotation) {
//                    try {
//                        throw new Exception("@FirebaseAuthKey annotation must be used only once!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                } else {
//                    existFirebaseAuthKeyAnnotation = true;
//                    try {
//                        field.setAccessible(true);
//                        authKey = field.get(obj1).toString();
//                        field.setAccessible(false);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                }
//            }
//
//            if (field.isAnnotationPresent(FirebaseId.class)) {
//                if (existFirebaseIdAnnotation) {
//                    try {
//                        throw new Exception("@FirebaseId annotation must be used only once!");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                } else {
//                    existFirebaseIdAnnotation = true;
//                    try {
//                        field.setAccessible(true);
//                        firebaseId = field.get(obj1).toString();
//                        field.setAccessible(false);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException();
//                    }
//                }
//            }
//
//        }
//
//        if (!existFirebaseAuthKeyAnnotation) {
//            try {
//                throw new Exception("There is no @FirebaseAuthKey annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//        if (authKey.isEmpty()) {
//            try {
//                throw new Exception("You have to set the Authentication Key!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//
//        if (!existFirebaseAuthKeyAnnotation) {
//            try {
//                throw new Exception("There is no @FirebaseId annotation!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//        if (firebaseId.isEmpty()) {
//            try {
//                throw new Exception("You have to set the Id!");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
//        }
//
//        String url = SecretConstants.FIREBASE_PROJECT_URL;
//        url = url + documentName + "/" + firebaseId + ".json?auth=" + authKey;
//
//        Class<DOC> documentClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
//
//        ResponseEntity<DOC> responseEntity = null;
//        try {
//            responseEntity = restTemplate.getForEntity(url, documentClass);
//        } catch (HttpStatusCodeException e) {
//            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
//                throw new HttpBadRequestException(e.getResponseBodyAsString());
//            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                throw new HttpNotFoundException(e.getResponseBodyAsString());
//            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//                throw new HttpUnauthorizedException(e.getResponseBodyAsString());
//            } else {
//                throw new RuntimeException();
//            }
//        }
//
//        if (responseEntity.getBody() == null) {
//            throw new HttpNotFoundException("FirebaseId Not Found");
//        }
//
//        return responseEntity.getBody();
//
//    }

}
