package com.mazy.videotools.service.impl;

import com.mazy.videotools.entity.VideoEvent;
import com.mazy.videotools.entity.VideoStatus;
import com.mazy.videotools.repository.VideoEventRepository;
import com.mazy.videotools.service.VideoEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class VideoEventServiceImpl implements VideoEventService {
    private final VideoEventRepository videoEventRepository;

    @Autowired
    public VideoEventServiceImpl(VideoEventRepository videoEventRepository) {
        this.videoEventRepository = videoEventRepository;
    }

    @Override
    @Transactional
    public VideoEvent createVideoEvent(String bucket, String key, String cognitoUserId) {
        String videoId = UUID.randomUUID().toString();

        VideoEvent videoEvent = VideoEvent.builder()
                .videoId(videoId)
                .cognitoUserId(cognitoUserId)
                .bucket(bucket)
                .key(key)
                .status(VideoStatus.INITIAL)
                .build();

        return this.videoEventRepository.save(videoEvent);
    }

    @Override
    public VideoEvent getVideoEventByVideoId(String videoId) {
        Optional<VideoEvent> videoEventOptional = this.videoEventRepository.findByVideoId(videoId);
        return videoEventOptional.orElse(null);
    }
}
