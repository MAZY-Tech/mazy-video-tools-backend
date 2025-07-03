package com.mazy.videotools.repository;

import com.mazy.videotools.entity.VideoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoEventRepository extends MongoRepository<VideoEvent, String> {
}
