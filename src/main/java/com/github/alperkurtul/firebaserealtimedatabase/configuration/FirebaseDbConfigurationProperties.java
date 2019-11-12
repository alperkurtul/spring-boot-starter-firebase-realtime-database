package com.github.alperkurtul.firebaserealtimedatabase.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("firebase-realtime-database")
public class FirebaseDbConfigurationProperties {
    private String databaseUrl;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

}
