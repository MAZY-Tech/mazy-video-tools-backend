package com.mazy.videotools.service.impl;

import com.mazy.videotools.entity.VideoEvent;
import com.mazy.videotools.repository.VideoEventRepository;
import com.mazy.videotools.service.VideoEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public VideoEvent createVideoEvent(String cognitoUserId, String bucket, String key) {
        String videoId = String.valueOf(UUID.randomUUID());

        VideoEvent videoEvent = VideoEvent.builder()
                .videoId(videoId)
                .cognitoUserId(cognitoUserId)
                .bucket(bucket)
                .key(key)
                .status("INITIAL")
                .build();

        return this.videoEventRepository.save(videoEvent);
    }

    @Override
    public VideoEvent getVideoEventByVideoId(String videoId) {
        return null;
    }
}
