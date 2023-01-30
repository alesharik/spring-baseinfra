package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileStorage;
import com.alesharik.spring.file.storage.SaveFileRequest;
import com.alesharik.spring.file.storage.exception.FileAlreadyExistsException;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import com.alesharik.spring.file.storage.exception.FileVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class InMemoryFileStorage implements FileStorage {
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char UNIX_SEPARATOR = '/';
    private final Map<String, byte[]> files = new ConcurrentHashMap<>();

    @Override
    @NonNull
    public String saveFile(@NonNull SaveFileRequest request) {
        var name = request.getFilenameOverride() != null ? request.getFilenameOverride()
                : (request.getFile().getOriginalFilename() == null || request.getFile().getOriginalFilename().isBlank())
                ? UUID.randomUUID() + ".dat"
                : getFilename(request.getFile().getOriginalFilename());
        var key = request.getType() + "/" + request.getItemId() + "/" + name;
        try {
            if (!request.isIgnoreExisting() && files.containsKey(key))
                throw new FileAlreadyExistsException();
            var data = request.getFile().getBytes();
            if (request.getVerifier() != null) {
                var tmp = Files.createTempFile(name, null);
                try {
                    Files.write(tmp, data);
                    if (!request.getVerifier().verifyFile(tmp, request.getFile())) {
                        throw new FileVerificationFailedException();
                    }
                } finally {
                    Files.delete(tmp);
                }
            }
            if (request.getConverter() != null) {
                data = request.getConverter()
                        .convert(new ByteArrayInputStream(data))
                        .readAllBytes();
            }
            files.put(key, data);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return name;
    }

    @NonNull
    @Override
    public FileSystemResource getFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        var key = type + "/" + itemId + "/" + id;
        var data = files.get(key);
        if (data != null)
            return new ResourceImpl(key, data);
        else
            throw new FileNotFoundException();
    }

    @Override
    public void deleteFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        var key = type + "/" + itemId + "/" + id;
        files.remove(key);
    }

    @Override
    @NonNull
    public String getLink(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        return "http://localhost:3000/api/staticv1/" + type + "/" + itemId + "/" + id;
    }

    @Override
    public boolean hasFile(@NonNull String type, @NonNull String itemId, @NonNull String filename) {
        var key = type + "/" + itemId + "/" + filename;
        return files.containsKey(key);
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

    private static final class ResourceImpl extends FileSystemResource {
        private final byte[] data;

        public ResourceImpl(String path, byte[] data) {
            super(path);
            this.data = data;
        }

        @Override
        @NonNull
        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }
    }
}
