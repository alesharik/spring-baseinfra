package com.alesharik.spring.file.storage.minio;

import com.alesharik.spring.common.minio.MinioClientTemplate;
import com.alesharik.spring.file.storage.FileStorage;
import com.alesharik.spring.file.storage.SaveFileRequest;
import com.alesharik.spring.file.storage.exception.FileAlreadyExistsException;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import com.alesharik.spring.file.storage.exception.FileVerificationFailedException;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class MinioFileStorage implements FileStorage {
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char UNIX_SEPARATOR = '/';
    private final MinioFileStorageProperties properties;
    private final MinioClientTemplate client;

    @Override
    @NonNull
    public String saveFile(@NonNull SaveFileRequest request) {
        var name = request.getFilenameOverride() != null ? request.getFilenameOverride()
                : (request.getFile().getOriginalFilename() == null || request.getFile().getOriginalFilename().isBlank())
                ? UUID.randomUUID() + ".dat"
                : getFilename(request.getFile().getOriginalFilename());
        var object = request.getType() + "/" + request.getItemId() + "/" + name;
        if (!request.isIgnoreExisting() && client.objectExists(properties.getBucket(), object))
            throw new FileAlreadyExistsException();
        if (request.getVerifier() != null) {
            Path file;
            try {
                file = Files.createTempFile("alesharik-spring-minio", ".tmp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                request.getFile().transferTo(file);
                if (!request.getVerifier().verifyFile(file, request.getFile())) {
                    throw new FileVerificationFailedException();
                }
                client.putFile(properties.getBucket(), object, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    log.error("Error while deleting temp file", e);
                    Sentry.captureException(e);
                    // ok :(
                }
            }
        } else {
            client.putMultipartFile(properties.getBucket(), object, request.getFile());
        }
        return name;
    }

    @Override
    @NonNull
    public FileSystemResource getFile(@NonNull String type, @NonNull String itemId, @NonNull String filename) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        var object = parseObject(type, itemId, id);
        client.deleteObject(properties.getBucket(), object);
    }

    @Override
    @NonNull
    public String getLink(@NonNull String type, @NonNull String itemId, @NonNull String filename) {
        return properties.getBaseBucketPath() + "/" + type + "/" + itemId + "/" + filename;
    }

    @Override
    public boolean hasFile(@NonNull String type, @NonNull String itemId, @NonNull String filename) {
        var object = parseObject(type, itemId, filename);
        return client.objectExists(properties.getBucket(), object);
    }

    private String parseObject(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        if (id.indexOf(UNIX_SEPARATOR) != -1 || id.indexOf(WINDOWS_SEPARATOR) != -1)
            throw new FileNotFoundException();
        return type + "/" + itemId + "/" + id;
    }

    private static String getFilename(String path) {
        int sep = path.lastIndexOf(UNIX_SEPARATOR);
        if (sep == -1)
            sep = path.lastIndexOf(WINDOWS_SEPARATOR);
        if (sep == -1)
            return path;
        else
            return path.substring(sep + 1);
    }
}
