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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoEventService videoEventService;

    public VideoController(VideoEventService videoEventService) {
        this.videoEventService = videoEventService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllVideos(Authentication auth) {
        String userId = auth.getName();

        List<VideoEvent> videoEvents = this.videoEventService.getVideoEventsByUserId(userId);
        List<VideoDTO> videos = new ArrayList<>();

        for (VideoEvent videoEvent : videoEvents) {
            VideoDTO dto = VideoDTO.fromEntity(videoEvent);
            dto.setDownloadUrl(this.videoEventService.getVideoDownloadUrl(videoEvent));
            videos.add(dto);
        }

        return ResponseEntity.ok(Map.of("videos", videos));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable("videoId") String videoId,
                                             Authentication auth) {
        VideoEvent videoEvent = this.videoEventService.getVideoEventByVideoId(videoId);
        if (videoEvent == null) {
            return ResponseEntity.notFound().build();
        }
        VideoDTO videoDTO = VideoDTO.fromEntity(videoEvent);
        videoDTO.setDownloadUrl(this.videoEventService.getVideoDownloadUrl(videoEvent));
        return ResponseEntity.ok(videoDTO);
    }
}
