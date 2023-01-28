package com.alesharik.spring.file.storage;

import org.springframework.lang.NonNull;

import java.io.InputStream;

/**
 * Convert file to target format
 */
public interface FileConverter {
    /**
     * Convert file
     * @param file file input stream
     * @return converted file as output stream
     * @throws com.alesharik.spring.file.storage.exception.FileConversionFailedException if file is bad, wrong format and etc
     */
    @NonNull
    InputStream convert(@NonNull InputStream file);
}
