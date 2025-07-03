package com.mazy.videotools.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mazy.videotools.entity.VideoEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VideoDTO {

    private String videoId;
    private String fileName;
    private String status;
    private String downloadUrl;
    private Integer progress;

    public VideoDTO() {
    }

    public VideoDTO(String videoId, String fileName, String status, String downloadUrl, Integer progress) {
        this.videoId = videoId;
        this.fileName = fileName;
        this.status = status;
        this.downloadUrl = downloadUrl;
        this.progress = progress;
    }

    public static VideoDTO fromEntity(VideoEvent videoEvent) {
        VideoDTO videoDTO = new VideoDTO();

        videoDTO.setFileName(videoEvent.getKey());
        videoDTO.setVideoId(videoEvent.getVideoId());
        videoDTO.setStatus(videoEvent.getStatus().toString());
        videoDTO.setProgress(videoEvent.getProgress());

        return videoDTO;
    }
}