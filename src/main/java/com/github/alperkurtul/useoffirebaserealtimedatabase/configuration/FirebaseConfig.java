package com.github.alperkurtul.useoffirebaserealtimedatabase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FirebaseConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseDbConfig firebaseDbConfig() {
//        return new FirebaseDbConfig();
//    }
}
