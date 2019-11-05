package com.github.alperkurtul.useoffirebaserealtimedatabase.repository.test;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseDbConfig;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.FirebaseAuthKeyAndDocumentId;
import com.github.alperkurtul.useoffirebaserealtimedatabase.model.Product;
import com.github.alperkurtul.useoffirebaserealtimedatabase.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@SpringBootTest(classes = FirebaseDbConfig.class)
@RunWith(SpringRunner.class)
@Import(ProductRepositoryTest.RepositoryTestConfiguration.class)
@EnableConfigurationProperties(FirebaseDbConfig.class)
public class ProductRepositoryTest {

    private String userAuthKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVkY2U3ZTQxYWRkMTIxYjg2ZWQ0MDRiODRkYTc1NzM5NDY3ZWQyYmMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZmx1dHRlci1wcm9kdWN0cy1hYzIwMyIsImF1ZCI6ImZsdXR0ZXItcHJvZHVjdHMtYWMyMDMiLCJhdXRoX3RpbWUiOjE1NzI3ODQ3MzMsInVzZXJfaWQiOiJJVHh5dXY5Q1lKaHA0SUVodnF2eFowYjZEQngyIiwic3ViIjoiSVR4eXV2OUNZSmhwNElFaHZxdnhaMGI2REJ4MiIsImlhdCI6MTU3Mjc4NDczMywiZXhwIjoxNTcyNzg4MzMzLCJlbWFpbCI6InRlc3Q3QHRlc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3Q3QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.sFziVJYsESswwTSWf0_9cBV-ipTm7dnGe0NqhHng_FD8fYfwu94tN043ZLg5vSOWM3UHCI0EclEjq4RdEo29uRG1DFU4v4EYHd-1bgeBHq2Wg3GrbECDCvRSqUvfcMQwzJi-Th930xHHipsGIkpPBsSCiL7xmhrWWo6hJb7khXrRXKNJXZ0uyPPN2eEAplemIyAfpEfKTNYY1Ijc4ZZLkXJAho8-ejiyISQ2Yz7BwwHnMmGh7xLw4pyy8rkL6KkfTSmGhVirc4XwjeRA5QuDsOSiDlTInAm_AFAz6FjnrLPTVTIsGvSOIN1fHN9qTOKonfL7oxAZJkQKZtz201KffQ";
    private String firebaseId = "";

    @TestConfiguration
    public static class RepositoryTestConfiguration {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public ProductRepository productRepository() {
            return new ProductRepository();
        }

    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void itShouldSaveReadUpdateDeleteAndSaveAgainWithSuccess() {

        FirebaseAuthKeyAndDocumentId firebaseAuthKeyAndDocumentId = new FirebaseAuthKeyAndDocumentId(this.userAuthKey, "");

        Product product = new Product();
        product.setId("JUnit-0001");
        product.setName("Spring Boot JUnit Test");
        product.setPrice(BigDecimal.valueOf(12,55));
        FirebaseSaveResponse savedResponse = productRepository.save(firebaseAuthKeyAndDocumentId, product);
        assertNotNull(savedResponse);
        assertNotNull(savedResponse.getName());
        assertNotEquals(savedResponse.getName(), "");
        this.firebaseId = savedResponse.getName();
        firebaseAuthKeyAndDocumentId.setFirebaseId(this.firebaseId);

        Product read = productRepository.read(firebaseAuthKeyAndDocumentId);
        assertNotNull(read);
        assertNotNull(read.getId());
        assertThat(read.getId(), is(product.getId()));

        product.setName("Spring Boot JUnit Test-UPDATED");
        productRepository.update(firebaseAuthKeyAndDocumentId, product);
        Product updatedResponse = productRepository.read(firebaseAuthKeyAndDocumentId);
        assertNotNull(updatedResponse);
        assertNotNull(updatedResponse.getId());
        assertThat(read.getId(), is(updatedResponse.getId()));
        assertThat(updatedResponse.getName(), is(product.getName()));

        productRepository.delete(firebaseAuthKeyAndDocumentId);
        try {
            Product deletedResponse = productRepository.read(firebaseAuthKeyAndDocumentId);
        } catch (HttpNotFoundException e) {
            //e.printStackTrace();
            assertThat(e.getMessage(), is("FirebaseDocumentId Not Found"));
        }


    }

}
