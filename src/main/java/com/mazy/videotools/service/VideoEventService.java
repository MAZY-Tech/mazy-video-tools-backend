package com.mazy.videotools.service;

import com.mazy.videotools.entity.VideoEvent;

import java.util.List;

public interface VideoEventService {
    VideoEvent createVideoEvent(String bucket, String key, String cognitoUserId);

    VideoEvent getVideoEventByVideoId(String videoId);

    String getVideoDownloadUrl(VideoEvent videoEvent);

    List<VideoEvent> getVideoEventsByUserId(String userId);
}
