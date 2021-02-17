package com.bogatikov.reactivespringsecurityfirebaseauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "firebase")
@Component
@Data
public class FirebaseConfig {

    private String serviceAccountFile;
}
