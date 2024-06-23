package com.alesharik.spring.common.minio;

public record SavedFile(
        String etag,
        String contentType
) {
}
