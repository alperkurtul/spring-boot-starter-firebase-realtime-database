package com.github.alperkurtul.firebaserealtimedatabase.repository.test;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseDbConfiguration;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseDbConfigurationProperties;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaserealtimedatabase.repository.model.Product;
import com.github.alperkurtul.firebaserealtimedatabase.repository.model.ProductRepository;
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

@SpringBootTest(classes = FirebaseDbConfiguration.class)
@RunWith(SpringRunner.class)
@Import(ProductRepositoryTest.RepositoryTestConfiguration.class)
@EnableConfigurationProperties(FirebaseDbConfigurationProperties.class)
public class ProductRepositoryTest {

    private String userAuthKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI1MDgxMWNkYzYwOWQ5MGY5ODE1MTE5MWIyYmM5YmQwY2ViOWMwMDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZmx1dHRlci1wcm9kdWN0cy1hYzIwMyIsImF1ZCI6ImZsdXR0ZXItcHJvZHVjdHMtYWMyMDMiLCJhdXRoX3RpbWUiOjE1NzM1OTI3MzksInVzZXJfaWQiOiJJVHh5dXY5Q1lKaHA0SUVodnF2eFowYjZEQngyIiwic3ViIjoiSVR4eXV2OUNZSmhwNElFaHZxdnhaMGI2REJ4MiIsImlhdCI6MTU3MzU5MjczOSwiZXhwIjoxNTczNTk2MzM5LCJlbWFpbCI6InRlc3Q3QHRlc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3Q3QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.WYGPhrZMVEVbFO4daVjx0FSOvCZd-7gXezmqtx4ruSRntR8rgYGRQksE_7TCQhM0FPwvkwntkkJ2p8LwbEJucFOzVIV7iKwekot5aZOWCJnMb7AOn078LFrVyHJb5VvKb0FnpyyZh-s_rI-XgTsBAwAwvIHenzleAPKEU8KBakW648UfvkbTQwF5c-bJAEA26Sv0Xd065BT6LvvtHq0bUG1LPccfiuvgx0PD47_5BU11ue-4-cm3YEqHTmlngwtSqNn-z5jKGrN6YL8Nv3eIoXds9zDreeqXg9m0LF4d4o4Z-AH44QrAaIVaRmrARsQVmujpgv57BOnKEMbD6k9F9w";
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

        Product product = new Product();
        product.setAuthKey(this.userAuthKey);
        product.setId("JUnit-0001");
        product.setName("Spring Boot JUnit Test");
        product.setPrice(BigDecimal.valueOf(12,55));
        FirebaseSaveResponse savedResponse = productRepository.save(product);
        assertNotNull(savedResponse);
        assertNotNull(savedResponse.getName());
        assertNotEquals(savedResponse.getName(), "");
        this.firebaseId = savedResponse.getName();
        product.setFirebaseId(this.firebaseId);

        Product read = productRepository.read(product);
        assertNotNull(read);
        assertNotNull(read.getId());
        assertThat(read.getId(), is(product.getId()));

        product.setName("Spring Boot JUnit Test-UPDATED");
        productRepository.update(product);
        Product updatedResponse = productRepository.read(product);
        assertNotNull(updatedResponse);
        assertNotNull(updatedResponse.getId());
        assertThat(read.getId(), is(updatedResponse.getId()));
        assertThat(updatedResponse.getName(), is(product.getName()));

        productRepository.delete(product);
        try {
            Product deletedResponse = productRepository.read(product);
        } catch (HttpNotFoundException e) {
            assertThat(e.getMessage(), is("FirebaseDocumentId Not Found"));
        }

    }

}
