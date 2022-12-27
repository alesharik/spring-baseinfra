package com.alesharik.spring.file.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

/**
 * This exception is thrown when file already has been added
 */
public class FileAlreadyExistsException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/file-storage/file-already-exists");

    public FileAlreadyExistsException() {
        super(HttpStatus.CONFLICT);
        setType(TYPE);
        setTitle("File already exists");
    }
}
