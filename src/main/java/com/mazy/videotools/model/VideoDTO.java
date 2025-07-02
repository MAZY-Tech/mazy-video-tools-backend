package com.mazy.videotools.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VideoDTO {

    private UUID videoId;
    private String status;
    private String downloadUrl;
    private double progress;

    public VideoDTO() {
    }

    public VideoDTO(UUID videoId, String status, String downloadUrl, double progress) {
        this.videoId = videoId;
        this.status = status;
        this.downloadUrl = downloadUrl;
        this.progress = progress;
    }

    public UUID getVideoId() {
        return videoId;
    }

    public void setVideoId(UUID videoId) {
        this.videoId = videoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}