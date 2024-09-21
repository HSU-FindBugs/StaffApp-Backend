package com.findbugs.findbugstaff.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            // Firebase가 이미 초기화되었는지 확인
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(getServiceAccountStream()))
                        .build();
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Firestore firestore() throws IOException {
        // FirestoreOptions를 명시적으로 초기화하고 프로젝트 ID를 설정합니다.
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance()
                .toBuilder()
                .setCredentials(GoogleCredentials.fromStream(getServiceAccountStream()))
                .setProjectId("findbugs-29ed3") // 여기에 프로젝트 ID 입력
                .build();
        return firestoreOptions.getService();
    }

    private InputStream getServiceAccountStream() {
        // Jenkins 환경 변수에서 Firebase JSON 내용을 가져와서 InputStream으로 변환합니다.
        String firebaseJson = System.getenv("FIREBASE_JSON_CONTENT");
        return new ByteArrayInputStream(firebaseJson.getBytes());
    }
}
