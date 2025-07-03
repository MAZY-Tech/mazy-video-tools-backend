package com.mazy.videotools.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "video_event")
public class VideoEvent {

    @Id
    private String id;

    @Field("bucket")
    private String bucket;

    @Field("key")
    private String key;

    @Field("file_name")
    private String fileName;

    @Field("cognito_user_id")
    private String cognitoUserId;

    @Field("last_update")
    private LocalDateTime lastUpdate;

    @Field("progress")
    private Integer progress;

    @Field("status")
    private VideoStatus status;

    @Field("timestamp")
    private OffsetDateTime timestamp;

    @Field("video_hash")
    private String videoHash;

    @Field("video_id")
    private String videoId;

    @Field("zip")
    private ZipInfo zip;

    @Data
    @Builder
    public static class ZipInfo {

        @Field("bucket")
        private String bucket;

        @Field("key")
        private String key;
    }
}
