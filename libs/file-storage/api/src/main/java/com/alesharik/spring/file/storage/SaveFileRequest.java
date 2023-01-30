package com.alesharik.spring.file.storage;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request to save file into storage
 * @see FileStorage
 */
@Data
@Builder
public final class SaveFileRequest {
    /**
     * Storage type
     */
    @NonNull
    private final String type;

    /**
     * Storage entity/directory
     */
    @NonNull
    private final String itemId;

    /**
     * File to store
     */
    @NonNull
    private final MultipartFile file;

    /**
     * If not null, this name is used to store file
     */
    private final String filenameOverride;

    /**
     * If true, overwrites existing file instead of throwing exception
     */
    private final boolean ignoreExisting;

    /**
     * Optional verifier to check file constraints (as example - check if file is JPEG)
     */
    @Nullable
    private final FileTypeVerifier verifier;

    /**
     * Optional converter to convert file to target format after verification
     */
    @Nullable
    private final FileConverter converter;
}
