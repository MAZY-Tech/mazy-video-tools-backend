package com.mazy.videotools.repository;

import com.mazy.videotools.entity.VideoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoEventRepository extends MongoRepository<VideoEvent, String> {
    Optional<VideoEvent> findByVideoId(String videoId);

    List<VideoEvent> findVideoEventByCognitoUserId(String cognitoUserId);
}
