package com.mazy.videotools.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VideoDTO {

    private UUID videoId;
    private String fileName;
    private String status;
    private String downloadUrl;
    private double progress;

    public VideoDTO() {
    }

    public VideoDTO(UUID videoId, String fileName, String status, String downloadUrl, double progress) {
        this.videoId = videoId;
        this.fileName = fileName;
        this.status = status;
        this.downloadUrl = downloadUrl;
        this.progress = progress;
    }
}