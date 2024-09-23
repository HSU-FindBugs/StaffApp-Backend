package com.findbugs.findbugstaff.controller;
import com.findbugs.findbugstaff.dto.Bug.BugDetectionRequestDto;
import com.findbugs.findbugstaff.implement.Detection.DetectionUpdater;
import com.findbugs.findbugstaff.service.BugDetailService;
import com.findbugs.findbugstaff.service.CameraService;
import com.findbugs.findbugstaff.service.DetectionService;
import com.findbugs.findbugstaff.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3ImageService s3ImageService;
    private final DetectionService detectionService;
    private final BugDetailService bugDetailService;

    @Operation(summary = "이미지 업로드", description = "주어진 staffId, memberId, detectionHistoryId에 따라 이미지를 S3에 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("cameraSerialNumber") String cameraSerialNumber,
            @RequestParam("bugId") Long bugId) {

        // 이미지 업로드 및 detectionHistory 업데이트
        String imageUrl = s3ImageService.upload(image, cameraSerialNumber, bugId);
        String bugNames = bugDetailService.sendBugName(bugId);
        Long memberID = bugDetailService.sendMemberId(cameraSerialNumber);
        BugDetectionRequestDto bugDetectionRequestDto = BugDetectionRequestDto.builder()
                        .memberId(memberID)
                        .bugName(bugNames).recentFindTime(LocalDateTime.now()).build();
        detectionService.handleBugDetection(bugDetectionRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(imageUrl);
    }


    @Operation(summary = "이미지 URL 조회", description = "주어진 staffId, memberId, detectionHistoryId에 따라 S3에서 이미지 URL을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 URL 조회 성공"),
            @ApiResponse(responseCode = "404", description = "이미지를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{staffId}/{memberId}/{detectionHistoryId}")
    public ResponseEntity<String> getImageUrl(
            @PathVariable String staffId,
            @PathVariable String memberId,
            @PathVariable String detectionHistoryId) {

        String imageUrl = s3ImageService.getImageUrl(staffId, memberId, detectionHistoryId);
        return imageUrl != null ? ResponseEntity.ok(imageUrl) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("이미지를 찾을 수 없습니다.");
    }
}
