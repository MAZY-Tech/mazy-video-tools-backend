package com.mazy.videotools.model;

import jakarta.validation.constraints.*;

public class PresignRequest {

    @NotBlank
    private String filename;

    @NotNull
    @Positive
    private Long sizeBytes;

    @NotNull
    @Positive
    private Integer durationSeconds;

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
}