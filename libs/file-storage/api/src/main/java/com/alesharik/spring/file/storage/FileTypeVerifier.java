package com.alesharik.spring.file.storage;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Verifies file type and contents
 */
public interface FileTypeVerifier {
    /**
     * Verifies file contents
     * @param path created file on disk
     * @param original original file source
     * @return <code>true</code> - file can be keep, <code>false</code> - file is bad and should be removed
     */
    boolean verifyFile(@NonNull Path path, @NonNull MultipartFile original);
}
