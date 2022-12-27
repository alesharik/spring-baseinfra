package com.alesharik.spring.common.minio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class MinioStorageException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/storage/minio/internal-error");

    public MinioStorageException(Exception exception) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        setType(TYPE);
        setTitle("Minio internal error");
    }
}
