package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileStorage;
import com.alesharik.spring.file.storage.SaveFileRequest;
import com.alesharik.spring.file.storage.exception.FileAlreadyExistsException;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import com.alesharik.spring.file.storage.exception.FileVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@RequiredArgsConstructor
public class LocalSelfHostedFileStorage implements FileStorage {
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char UNIX_SEPARATOR = '/';
    private final LocalSelfHostedFileStorageProperties properties;

    @Override
    @NonNull
    public String saveFile(@NonNull SaveFileRequest request) {
        var dir = getTypeDirectory(request.getType(), request.getItemId(), true);
        var name = request.getFilenameOverride() != null ? request.getFilenameOverride()
                : (request.getFile().getOriginalFilename() == null || request.getFile().getOriginalFilename().isBlank())
                ? UUID.randomUUID() + ".dat"
                : getFilename(request.getFile().getOriginalFilename());
        var path = dir.resolve(Path.of(name));
        try {
            if (!request.isIgnoreExisting() && Files.exists(path))
                throw new FileAlreadyExistsException();

            var inputStream = request.getFile().getInputStream();
            Path tmpFile = null;
            if (request.getVerifier() != null) {
                var tmp = Files.createTempFile(name, null);
                try {
                    request.getFile().transferTo(path);
                    if (!request.getVerifier().verifyFile(tmp, request.getFile())) {
                        Files.delete(tmp);
                        throw new FileVerificationFailedException();
                    }
                    inputStream.close();
                    inputStream = Files.newInputStream(tmp);
                    tmpFile = tmp;
                } catch (Throwable e) {
                    Files.deleteIfExists(tmp);
                    throw e;
                }
            }

            if (request.getConverter() != null) {
                var newStream = request.getConverter().convert(inputStream);
                inputStream.close();
                inputStream = newStream;
            }
            if (!Files.exists(path))
                Files.createFile(path);
            inputStream.transferTo(Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING));
            inputStream.close();

            if (tmpFile != null)
                Files.deleteIfExists(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return name;
    }

    @NonNull
    @Override
    public FileSystemResource getFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        var file = parseFile(type, itemId, id);
        if (Files.exists(file))
            return new FileSystemResource(file);
        else
            throw new FileNotFoundException();
    }

    @Override
    public void deleteFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        try {
            var file = parseFile(type, itemId, id);
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            // seems like file already deleted :/
        }
    }

    @Override
    @NonNull
    public String getLink(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        return properties.getBasePath() + "/api/staticv1/" + type + "/" + itemId + "/" + id;
    }

    @Override
    public boolean hasFile(@NonNull String type, @NonNull String itemId, @NonNull String filename) {
        var file = parseFile(type, itemId, filename);
        return Files.exists(file);
    }

    private Path parseFile(@NonNull String type, @NonNull String itemId, @NonNull String id) {
        if (id.indexOf(UNIX_SEPARATOR) != -1 || id.indexOf(WINDOWS_SEPARATOR) != -1)
            throw new FileNotFoundException();
        return getTypeDirectory(type, itemId, false).resolve(Path.of(id));
    }

    private Path getTypeDirectory(@NonNull String type, @NonNull String itemId, boolean create) {
        var path = Path.of(properties.getPath(), type, itemId);
        if (!Files.exists(path)) {
            if (!create)
                throw new FileNotFoundException();
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
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
