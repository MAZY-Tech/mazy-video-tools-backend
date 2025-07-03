package com.mazy.videotools.repository;

import com.mazy.videotools.entity.VideoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideoEventRepository extends MongoRepository<VideoEvent, String> {
    Optional<VideoEvent> findByVideoId(UUID videoId);
}
