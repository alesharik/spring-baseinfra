package com.alesharik.spring.file.storage.minio;

public record SavedFile(
        String name,
        String contentType,
        String etag
) {
}
