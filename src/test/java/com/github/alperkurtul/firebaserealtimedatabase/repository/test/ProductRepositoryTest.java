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

    private String userAuthKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhhMzY5M2YxMzczZjgwYTI1M2NmYmUyMTVkMDJlZTMwNjhmZWJjMzYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZmx1dHRlci1wcm9kdWN0cy1hYzIwMyIsImF1ZCI6ImZsdXR0ZXItcHJvZHVjdHMtYWMyMDMiLCJhdXRoX3RpbWUiOjE1NzM5NzU1OTYsInVzZXJfaWQiOiJJVHh5dXY5Q1lKaHA0SUVodnF2eFowYjZEQngyIiwic3ViIjoiSVR4eXV2OUNZSmhwNElFaHZxdnhaMGI2REJ4MiIsImlhdCI6MTU3Mzk3NTU5NiwiZXhwIjoxNTczOTc5MTk2LCJlbWFpbCI6InRlc3Q3QHRlc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3Q3QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.UEQC035OL2G0DlVIy7jRPSWQccmotiXlI62wiIox3NulqIyv-gDeFiFxwxm-0Gpk0DalWxJfS5LiHM7DvaxHNX4pmnmY92fwtIIJjsqN7DelQG854HQGMhr9VTU-opWMdrMKCsEIvgrCKh0zld7FGpfkBo5eTRqrBrXDrnl61hG88f3MQBH_HeAscT-Pqe4U1eKjtELS6G54Wl7BHrC9QMbaIQUiPPfFAY127IMSKv5d-htABGvHw847o1VeQzZonaK8WVE-zC_IGjCXU8VLHLko-0s3wg1zqknGvuKMJC-orrcdFJvS03CyWZsKT7aBPi27X7PN4Uf6nenSQNyL6A";
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
