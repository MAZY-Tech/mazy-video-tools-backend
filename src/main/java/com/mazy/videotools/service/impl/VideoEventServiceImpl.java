package com.mazy.videotools.service.impl;

import com.mazy.videotools.entity.VideoEvent;
import com.mazy.videotools.entity.VideoStatus;
import com.mazy.videotools.repository.VideoEventRepository;
import com.mazy.videotools.service.VideoEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoEventServiceImpl implements VideoEventService {
    private final VideoEventRepository videoEventRepository;
    private final S3Service s3Service;
    private static Integer DEFAULT_VIDEO_EXPIRATION = 15;

    @Autowired
    public VideoEventServiceImpl(VideoEventRepository videoEventRepository, S3Service s3Service) {
        this.videoEventRepository = videoEventRepository;
        this.s3Service = s3Service;
    }

    @Override
    @Transactional
    public VideoEvent createVideoEvent(String bucket, String key, String fileName, String cognitoUserId) {
        String videoId = UUID.randomUUID().toString();

        VideoEvent videoEvent = VideoEvent.builder()
                .videoId(videoId)
                .bucket(bucket)
                .key(key)
                .fileName(fileName)
                .cognitoUserId(cognitoUserId)
                .status(VideoStatus.INITIAL)
                .build();

        return this.videoEventRepository.save(videoEvent);
    }

    @Override
    public VideoEvent getVideoEventByVideoId(String videoId) {
        Optional<VideoEvent> videoEventOptional = this.videoEventRepository.findByVideoId(videoId);
        return videoEventOptional.orElse(null);
    }

    public String getVideoDownloadUrl(VideoEvent videoEvent) {
        if (videoEvent.getZip() != null) {
            Duration expiration = Duration.ofMinutes(DEFAULT_VIDEO_EXPIRATION);
            return String.valueOf(
                    s3Service.generatePresignedFromBucketKeyGetUrl(
                            videoEvent.getZip().getBucket(),
                            videoEvent.getZip().getKey(),
                            expiration
                    )
            );
        }
        return null;
    }

    @Override
    public List<VideoEvent> getVideoEventsByUserId(String userId) {
        return this.videoEventRepository.findVideoEventByCognitoUserId(userId);
    }
}
