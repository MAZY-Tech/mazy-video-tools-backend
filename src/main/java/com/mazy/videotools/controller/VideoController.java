package com.mazy.videotools.controller;

import com.mazy.videotools.dto.VideoDTO;
import com.mazy.videotools.entity.VideoEvent;
import com.mazy.videotools.service.VideoEventService;
import com.mazy.videotools.service.impl.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoEventService videoEventService;
    private final S3Service s3Service;

    public VideoController(VideoEventService videoEventService, S3Service s3Service) {
        this.videoEventService = videoEventService;
        this.s3Service = s3Service;
    }

    @GetMapping()
    public ResponseEntity<?> getAllVideos(Authentication auth) {
        String userId = auth.getName();

        List<VideoDTO> videos = List.of(
                new VideoDTO(UUID.randomUUID().toString(), "file.mp4", "PROCESSING", null, 0),
                new VideoDTO(UUID.randomUUID().toString(), "video-teste.mp4", "COMPLETED", "https://mazy-bucket.s3.us-east-1.amazonaws.com/video-teste.mp4", 100)
        );


        return ResponseEntity.ok(Map.of("videos", videos));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable("videoId") String videoId,
                                             Authentication auth) {
        VideoEvent videoEvent = this.videoEventService.getVideoEventByVideoId(videoId);
        if (videoEvent == null) {
            return ResponseEntity.notFound().build();
        }
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setFileName(videoEvent.getKey());
        videoDTO.setVideoId(videoEvent.getVideoId());
        videoDTO.setStatus(videoEvent.getStatus().toString());
        videoDTO.setProgress(videoEvent.getProgress());

        if (videoEvent.getZip() != null) {
            Duration expiration = Duration.ofMinutes(15);
            String downloadUrl = String.valueOf(
                    s3Service.generatePresignedFromBucketKeyGetUrl(
                            videoEvent.getZip().getBucket(),
                            videoEvent.getZip().getKey(),
                            expiration
                    )
            );
            videoDTO.setDownloadUrl(downloadUrl);
        }
        return ResponseEntity.ok(videoDTO);
    }
}
