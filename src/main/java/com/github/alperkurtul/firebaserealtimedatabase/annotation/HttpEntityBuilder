package com.github.alperkurtul.firebaserealtimedatabase.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alperkurtul.firebaserealtimedatabase.exception.FirebaseRepositoryException;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class HttpEntityBuilder<T> {

    private final ObjectMapper firebaseObjectMapper;

    private T document;

    private MultiValueMap<String, String> headers;

    private HttpEntityBuilder(ObjectMapper firebaseObjectMapper) {
        this.firebaseObjectMapper = firebaseObjectMapper;
    }

    public static HttpEntityBuilder create(ObjectMapper firebaseObjectMapper) {
        return new HttpEntityBuilder(firebaseObjectMapper);
    }

    public HttpEntityBuilder document(T document) {
        this.document = document;
        return this;
    }

    public HttpEntityBuilder header(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
        return this;
    }

    public HttpEntity<String> build() {
        try {
            if(document == null) {
                return new HttpEntity<>(headers);
            } else {
                HttpEntity httpEntity = new HttpEntity<>(firebaseObjectMapper.writeValueAsString(document), headers);
                return httpEntity;
            }
        } catch (IOException e) {
            throw new FirebaseRepositoryException(e.getMessage());
        }
    }

}
