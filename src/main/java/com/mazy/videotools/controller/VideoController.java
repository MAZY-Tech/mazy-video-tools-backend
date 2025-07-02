package com.mazy.videotools.controller;

import com.mazy.videotools.model.VideoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping( "/api/videos")
public class VideoController {

    @GetMapping()
    public ResponseEntity<?> getAllVideos(Authentication auth) {
        String userId = auth.getName();

        List<VideoDTO> videos = List.of(
                new VideoDTO(UUID.randomUUID(), "processing", null, 0.0),
                new VideoDTO(UUID.randomUUID(), "completed", "https://example.com/video.mp4", 100.0)
        );


        return ResponseEntity.ok(Map.of("videos", videos));
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable("videoId") UUID videoId,
                                             Authentication auth) {

        var video = new VideoDTO();
        video.setVideoId(videoId);
        video.setStatus("COMPLETED");
        video.setProgress(1);
        video.setDownloadUrl(null); // Initially no download URL

        return ResponseEntity.ok(video);
    }
}
