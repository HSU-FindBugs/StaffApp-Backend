package com.findbugs.findbugstaff.service;

import com.findbugs.findbugstaff.domain.Bug;
import com.findbugs.findbugstaff.domain.Camera;
import com.findbugs.findbugstaff.dto.Bug.BugDetailDto;
import com.findbugs.findbugstaff.dto.Bug.BugSolutionDto;
import com.findbugs.findbugstaff.implement.Camera.CameraFinder;
import com.findbugs.findbugstaff.implement.Member.MemberSearcher;
import com.findbugs.findbugstaff.repository.BugRepository;
import com.findbugs.findbugstaff.repository.CameraRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BugDetailService {

    private final Firestore firestore;
    private final BugRepository bugRepository;
    private final MemberSearcher memberSearcher;
    private final CameraFinder cameraFinder;
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

    public BugSolutionDto getBugSolution(String bugName){
        DocumentReference docRef = firestore.collection("solution").document(bugName);
        // 비동기적으로 문서 가져오기
        ApiFuture<DocumentSnapshot> future = docRef.get();

        try {
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                System.out.println("data: " + document.getData());
                Map<String, Object> data = document.getData();
                BugSolutionDto bugSolutionDto = BugSolutionDto.builder()
                        .firstSolution((String) data.get("1단계"))  // nosql의 appearance 필드
                        .secondSolution((String) data.get("2단계"))  // nosql의 inhabitation 필드
                        .thirdSolution((String) data.get("3단계"))  // nosql의 quarantine 필드
                        .fourSolution((String) data.get("4단계"))  // nosql의 quarantine 필드
                        .build();

                return bugSolutionDto;
            } else {
                System.out.println("데이터 서치 에러, 존재하지 않는 데이터입니다.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendBugName(Long bugId) {
        return bugRepository.findById(bugId)
                .map(Bug::getName)
                .orElse("버그를 찾을 수 없습니다."); // 기본값
    }

    public Long sendMemberId(String cameraSerialNumber){
        Camera camera = cameraFinder.findByCameraSerialNum(cameraSerialNumber);
        return camera.getMember().getId();

    }




}
