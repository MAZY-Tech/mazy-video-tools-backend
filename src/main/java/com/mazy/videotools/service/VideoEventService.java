package com.mazy.videotools.service;

import com.mazy.videotools.entity.VideoEvent;

import java.util.UUID;

public interface VideoEventService {
    VideoEvent createVideoEvent(String bucket, String key, String cognitoUserId);
    VideoEvent getVideoEventByVideoId(UUID videoId);
}
