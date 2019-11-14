package com.github.alperkurtul.firebaserealtimedatabase;

import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseRealtimeDatabaseConfiguration;
import com.github.alperkurtul.firebaserealtimedatabase.configuration.FirebaseRealtimeDatabaseConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(classes = FirebaseRealtimeDatabaseConfiguration.class)
@TestPropertySource(locations = "classpath:application.properties")
@EnableConfigurationProperties(FirebaseRealtimeDatabaseConfigurationProperties.class)
public @interface FirebaseRealtimeDatabaseTest {
}
