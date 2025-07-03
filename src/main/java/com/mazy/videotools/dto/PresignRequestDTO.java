package com.mazy.videotools.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

public class PresignRequestDTO {

    @NotBlank
    private String filename;

    @NotNull
    @Positive
    private Long sizeBytes;

    @NotNull
    @Positive
    private Integer durationSeconds;

    @NotBlank
    @JsonProperty("Content-Type")
    private String contentType;

    @NotBlank
    @JsonProperty("x-amz-meta-video_hash")
    private String xAmzMetaVideoHash;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getXAmzMetaVideoHash() {
        return xAmzMetaVideoHash;
    }

    public void setXAmzMetaVideoHash(String xAmzMetaVideoHash) {
        this.xAmzMetaVideoHash = xAmzMetaVideoHash;
    }
}