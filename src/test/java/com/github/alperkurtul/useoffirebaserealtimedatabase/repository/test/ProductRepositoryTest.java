package com.github.alperkurtul.useoffirebaserealtimedatabase.repository.test;

import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseDbConfig;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = FirebaseDbConfig.class)
@RunWith(SpringRunner.class)
@Import(ProductRepositoryTest.RepositoryTestConfiguration.class)
@EnableConfigurationProperties(FirebaseDbConfig.class)
public class ProductRepositoryTest {

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
    public void itShouldReadWithSuccess() {

        String userAuthKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjVkY2U3ZTQxYWRkMTIxYjg2ZWQ0MDRiODRkYTc1NzM5NDY3ZWQyYmMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZmx1dHRlci1wcm9kdWN0cy1hYzIwMyIsImF1ZCI6ImZsdXR0ZXItcHJvZHVjdHMtYWMyMDMiLCJhdXRoX3RpbWUiOjE1NzI3NzIwNDcsInVzZXJfaWQiOiJJVHh5dXY5Q1lKaHA0SUVodnF2eFowYjZEQngyIiwic3ViIjoiSVR4eXV2OUNZSmhwNElFaHZxdnhaMGI2REJ4MiIsImlhdCI6MTU3Mjc3MjA0NywiZXhwIjoxNTcyNzc1NjQ3LCJlbWFpbCI6InRlc3Q3QHRlc3QuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3Q3QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.V39l4-YUfPlUR15dH07E6D58jXc2UcVRh274roG--OH36K5NrKgkYADX54cEPBzjobYPHTGcE08S6k11KFUTlqtPyFOC_xIPbujqe5cwlGtjKWiGTH2dJK3G2e1E71Jra0VdhHMVvR77HQiyP7Z3WD8D9IFU0_gljNmlfqjuxQdNEMBng-aLY8PlemR3EFOKpmO6XQ2UA3gOvg1aKCmdJGyQ1hwe4NFxuEDCFiT24OKzIeSqX9rYK3n-_CtdiY16WBeiSlFl07MONksjM3TWAkbfS66Ur6r72Ca8XZO73rSQz1oKbJ9iYfikqZyN2RVaWyAiJrMp7cDwheXYqfdaZA";
        FirebaseAuthKeyAndDocumentId firebaseAuthKeyAndDocumentId = new FirebaseAuthKeyAndDocumentId(userAuthKey, "-Ls7V6RMigns_4TpR7pw");
        Product read = productRepository.read(firebaseAuthKeyAndDocumentId);

        assertNotNull(read);
        assertNotNull(read.getId());
        assertThat(read.getId(), is("1003"));

    }

}
