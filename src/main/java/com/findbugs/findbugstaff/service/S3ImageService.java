package com.findbugs.findbugstaff.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${AWS_S3_BUCKET}")
    private String bucketName;


    public String getImageUrl(String staffId, String memberId, String detectionHistoryId) {
        String prefix = String.format("%s/%s/%s", staffId, memberId, detectionHistoryId);

        List<S3ObjectSummary> objectSummaries = amazonS3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(prefix)).getObjectSummaries();

        // 파일명에서 '_' 앞까지 동일한 첫 번째 파일을 찾기
        for (S3ObjectSummary objectSummary : objectSummaries) {
            String key = objectSummary.getKey();
            if (key.startsWith(prefix) && key.substring(prefix.length()).contains("_")) {
                return amazonS3.getUrl(bucketName, key).toString();
            }
        }

        // 해당하는 파일이 없는 경우 null 또는 예외 처리
        return null; // 또는 throw new RuntimeException("Image not found.");
    }



    public String upload(MultipartFile image, String staffId, String memberId, String detectionHistoryId) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new MultipartException("Uploaded file is empty or missing filename.");
        }
        return this.uploadImage(image, staffId, memberId, detectionHistoryId);
    }

    private String uploadImage(MultipartFile image, String staffId, String memberId, String detectionHistoryId) {
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image, staffId, memberId, detectionHistoryId);
        } catch (IOException e) {
            throw new MultipartException("IO exception occurred during image upload.", e);
        }
    }

    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new MultipartException("No file extension found.");
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new MultipartException("Invalid file extension. Allowed extensions are: " + allowedExtentionList);
        }
    }

    private String uploadImageToS3(MultipartFile image, String staffId, String memberId, String detectionHistoryId) throws IOException {
        String originalFilename = image.getOriginalFilename(); // 원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자 명

        // S3 파일 경로 구성: staffId/memberId/detectionHistoryId_UUID.확장자
        String s3FileName = String.format("%s/%s/%s_%s%s",
                staffId,
                memberId,
                detectionHistoryId,
                UUID.randomUUID().toString().substring(0, 10),
                extention);

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest); // put image to S3
        } catch (Exception e) {
            throw new MultipartException("Failed to upload object to S3.", e);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (Exception e) {
            throw new MultipartException("IO exception occurred during image delete.", e);
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new MultipartException("IO exception occurred while decoding image address.", e);
        }
    }
}
