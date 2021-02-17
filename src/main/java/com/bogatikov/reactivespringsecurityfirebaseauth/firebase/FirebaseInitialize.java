package com.bogatikov.reactivespringsecurityfirebaseauth.firebase;

import com.bogatikov.reactivespringsecurityfirebaseauth.config.FirebaseConfig;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseInitialize {

    private final FirebaseConfig firebaseConfig;

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig.getServiceAccountFile())
                                    .getInputStream()
                            )
                    )
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialize successfully");
            }
        } catch (IOException e) {
            log.error("Firebase initialization exception", e);
            throw new BeanCreationException(FirebaseInitialize.class.getSimpleName(), "Firebase app instance configuration failed");
        }
    }

}
