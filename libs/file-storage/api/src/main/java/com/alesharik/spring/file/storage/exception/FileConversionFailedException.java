package com.alesharik.spring.file.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

/**
 * This exception is thrown if file does not satisfy some constraints. As example, if JPEG file is required, all other
 * formats will receive this exception
 */
public class FileConversionFailedException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://docs.alesharik.com/docs/error/file-storage/file-conversion-failed");

    public FileConversionFailedException() {
        super(HttpStatus.BAD_REQUEST);
        setType(TYPE);
        setTitle("File conversion failed");
        setDetail("File has unsupported format or bad content");
    }
}
