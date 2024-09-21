package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.dto.Bug.BugDetailDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BugDetailService {

    private final Firestore firestore;
    public BugDetailDto getBugDataByName(String bugName) {
        DocumentReference docRef = firestore.collection("bugs").document(bugName);
        // 비동기적으로 문서 가져오기
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                System.out.println("data: " + document.getData());
                Map<String, Object> data = document.getData();
                BugDetailDto bugDetailDto = BugDetailDto.builder()
                        .appearance((String) data.get("appearance"))  // nosql의 appearance 필드
                        .inhabitation((String) data.get("inhabitation"))  // nosql의 inhabitation 필드
                        .quarantine((String) data.get("quarantine"))  // nosql의 quarantine 필드
                        .build();

                return bugDetailDto;
            } else {
                System.out.println("데이터 서치 에러, 존재하지 않는 데이터입니다.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
