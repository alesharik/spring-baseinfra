package com.alesharik.spring.file.storage;

import com.alesharik.spring.file.storage.exception.FileAlreadyExistsException;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;

/**
 * Universal file storage. It stores files in separated directories/entities, divided by groups (file storage types).
 * As example, post icon will live in <code>post</code> file storage type, <code>${post.id}</code> entity id, <code>icon.jpg</code> filename
 */
public interface FileStorage {
    /**
     * Save file
     * @param request parameters
     * @return saved filename
     * @throws FileAlreadyExistsException if passed file already exists
     */
    @NonNull
    String saveFile(@NonNull SaveFileRequest request);

    /**
     * Get file resource
     * @param type file storage type
     * @param itemId file storage entity/directory
     * @param filename target filename
     * @return resource pointing to requested file
     * @throws FileNotFoundException if requested file does not exist
     */
    @NonNull
    FileSystemResource getFile(@NonNull String type, @NonNull String itemId, @NonNull String filename);

    /**
     * Delete file. Ignores all errors
     * @param type file storage type
     * @param itemId file storage entity/directory
     * @param filename filename
     */
    void deleteFile(@NonNull String type, @NonNull String itemId, @NonNull String filename);

    /**
     * Get public HTTP link pointing to file
     * @param type file storage type
     * @param itemId file storage entity/directory
     * @param filename filename
     * @return link to file
     */
    @NonNull
    String getLink(@NonNull String type, @NonNull String itemId, @NonNull String filename);

    /**
     * Check if file exists
     * @param type file storage type
     * @param itemId file storage entity/directory
     * @param filename filename
     * @return <code>true</code> if file exists, <code>false</code> otherwise
     */
    boolean hasFile(@NonNull String type, @NonNull String itemId, @NonNull String filename);
}
