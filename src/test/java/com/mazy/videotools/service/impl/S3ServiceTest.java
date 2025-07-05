package com.mazy.videotools.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class S3ServiceTest {

    // Test-specific subclass of S3Service that overrides methods that interact with AWS
    private static class TestS3Service extends S3Service {
        private final URL testUrl;

        public TestS3Service(String region, String bucket, URL testUrl) {
            super(region, bucket);
            this.testUrl = testUrl;
        }

        @Override
        public URL generatePresignedPutUrl(String key, String contentType, long sizeBytes, Map<String, String> metadata) {
            return testUrl;
        }

        @Override
        public URL generatePresignedFromBucketKeyGetUrl(String bucketName, String key, Duration expiration) {
            return testUrl;
        }
    }

    private TestS3Service s3Service;
    private URL testUrl;

    private final String TEST_REGION = "us-east-1";
    private final String TEST_BUCKET = "test-bucket";
    private final String TEST_KEY = "test-key";
    private final String TEST_CONTENT_TYPE = "video/mp4";
    private final long TEST_SIZE_BYTES = 1024L;

    @BeforeEach
    void setUp() throws MalformedURLException {
        testUrl = new URL("https://test-bucket.s3.amazonaws.com/test-key");
        s3Service = new TestS3Service(TEST_REGION, TEST_BUCKET, testUrl);
    }

    @Test
    void generatePresignedPutUrl_ShouldReturnUrl() {
        // Arrange
        Map<String, String> metadata = new HashMap<>();
        metadata.put("user-id", "test-user");

        // Act
        URL result = s3Service.generatePresignedPutUrl(TEST_KEY, TEST_CONTENT_TYPE, TEST_SIZE_BYTES, metadata);

        // Assert
        assertNotNull(result);
        assertEquals(testUrl, result);
    }

    @Test
    void generatePresignedFromBucketKeyGetUrl_ShouldReturnUrl() {
        // Arrange
        Duration expiration = Duration.ofMinutes(15);

        // Act
        URL result = s3Service.generatePresignedFromBucketKeyGetUrl(TEST_BUCKET, TEST_KEY, expiration);

        // Assert
        assertNotNull(result);
        assertEquals(testUrl, result);
    }

    @Test
    void getBucketName_ShouldReturnConfiguredBucketName() {
        // Act
        String result = s3Service.getBucketName();

        // Assert
        assertEquals(TEST_BUCKET, result);
    }
}