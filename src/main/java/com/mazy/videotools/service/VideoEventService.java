package com.mazy.videotools.service;

import com.mazy.videotools.entity.VideoEvent;

public interface VideoEventService {
    VideoEvent createVideoEvent(String bucket, String key, String cognitoUserId);

    VideoEvent getVideoEventByVideoId(String videoId);
}
