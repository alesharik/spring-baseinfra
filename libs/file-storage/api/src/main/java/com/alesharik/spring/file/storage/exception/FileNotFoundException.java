package com.alesharik.spring.file.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

/**
 * This exception is thrown when requested file does not exist
 */
public class FileNotFoundException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://docs.alesharik.com/docs/error/file-storage/file-not-found");

    public FileNotFoundException() {
        super(HttpStatus.NOT_FOUND);
        setType(TYPE);
        setTitle("File not found");
    }
}
