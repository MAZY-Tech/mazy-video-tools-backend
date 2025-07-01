package com.mazy.videotools.controller;

import com.mazy.videotools.model.PresignRequest;
import com.mazy.videotools.service.S3Service;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UploadController {

    private final S3Service s3Service;

    private static final List<String> ALLOWED_EXTENSIONS = List.of("mp4", "mov", "avi", "mkv");

    private static final long MAX_SIZE_BYTES = 2L * 1024L * 1024L * 1024L; // 2 GB
    private static final int MAX_DURATION_SECONDS = 60 * 60; // 60 minutes

    public UploadController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/presign-upload")
    public ResponseEntity<?> createPresignedUrl(@Valid @RequestBody PresignRequest req, Authentication auth) {
        String userId = auth.getName();

        String extension = req.getFilename().substring(req.getFilename().lastIndexOf('.') + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unsupported video format"));
        }

        if (req.getSizeBytes() > MAX_SIZE_BYTES) {
            return ResponseEntity.badRequest().body(Map.of("error", "File exceeds size limit of 2 GB"));
        }

        if (req.getDurationSeconds() > MAX_DURATION_SECONDS) {
            return ResponseEntity.badRequest().body(Map.of("error", "Video duration exceeds 60 minutes"));
        }

        String key = "%s/%d_%s".formatted(userId, Instant.now().toEpochMilli(), req.getFilename());

        Map<String, String> metadata = Map.of(
            "video_hash", req.getXAmzMetaVideoHash(),
            "cognito_user_id", userId
        );
        URL url = s3Service.generatePresignedPutUrl(key, req.getContentType(), req.getSizeBytes(), metadata);

        return ResponseEntity.ok(Map.of(
                "uploadUrl", url.toString(),
                "s3Key", key,
                "expiresInMinutes", 15
        ));
    }
}