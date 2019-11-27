package com.github.alperkurtul.firebaserealtimedatabase.repository.test;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.github.alperkurtul.firebaserealtimedatabase.exception.HttpNotFoundException;
import com.github.alperkurtul.firebaserealtimedatabase.FirebaseRealtimeDatabaseTest;
import com.github.alperkurtul.firebaserealtimedatabase.model.Product;
import com.github.alperkurtul.firebaserealtimedatabase.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@FirebaseRealtimeDatabaseTest
@Import(ProductRepositoryTest.RepositoryTestConfiguration.class)
public class ProductRepositoryTest {

    private String userAuthKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjRhOWEzMGI5ZThkYTMxNjY2YTY3NTRkZWZlZDQxNzQzZjJlN2FlZWEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZmx1dHRlci1wcm9kdWN0cy1hYzIwMyIsImF1ZCI6ImZsdXR0ZXItcHJvZHVjdHMtYWMyMDMiLCJhdXRoX3RpbWUiOjE1NzQ4ODc0NjYsInVzZXJfaWQiOiJJVHh5dXY5Q1lKaHA0SUVodnF2eFowYjZEQngyIiwic3ViIjoiSVR4eXV2OUNZSmhwNElFaHZxdnhaMGI2REJ4MiIsImlhdCI6MTU3NDg4NzQ2NiwiZXhwIjoxNTc0ODkxMDY2LCJlbWFpbCI6InRlc3Q3QHRlc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3Q3QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.Y-7IiwWM1dOtEORwl2LX97GPEPAtkwxAEMQTzSjbhn4uf2c22-BtlQA3xe37b6WZ0ZAHDRM-98Gq-AjzUwoF7DjYd1t2Rt7Sg2wFbyMG3ov0zKe3GC-ROxagmzUp2-P_m1fiiS4s0asvvvOp_tMketLpi8EhXIz_9eQmLmRIBTwUAJ9d48BPGyU2D0HX_7JFKF3S2fAXyWF5uLoSQhN-K8rEIV8QKE65l1L9sRhJ-PpsS-gpp3TzDy_UtG___z9i-_krzSyPhf1FyDG69YCHDLUSespoS_j09ajzBrn5amw261vBAqANZDFxvuBB5q-JMutgF6XjlP_-1TWSIripmQ";
    private String firebaseId = "";

    @TestConfiguration
    public static class RepositoryTestConfiguration {

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
        product.setFirebaseId("11111");
        product.setId("JUnit-0001");
        product.setName("Spring Boot JUnit Test");
        product.setPrice(BigDecimal.valueOf(12.40));
        productRepository.saveWithSpecificId(product);

        product = new Product();
        product.setAuthKey(this.userAuthKey);
        product.setFirebaseId("AAA");
        product.setId("JUnit-0001");
        product.setName("Spring Boot JUnit Test");
        product.setPrice(BigDecimal.valueOf(12.55));
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
