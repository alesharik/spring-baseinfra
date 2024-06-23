package com.alesharik.spring.common.minio;

import com.alesharik.spring.common.minio.exception.MinioStorageException;
import io.minio.*;
import io.minio.errors.*;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Minio client wrapper
 */
@Slf4j
@RequiredArgsConstructor
public class MinioClientTemplate {
    private final Tika tika = new Tika();
    private final MinioClient client;

    /**
     * Check if object exists
     * @param bucket bucket
     * @param object object name
     * @return <code>true</code> if it exists, <code>false</code> otherwise
     */
    public boolean objectExists(@NonNull String bucket, @NonNull String object) {
        try {
            client.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .build()
            );
            return true;
        } catch (ServerException | InsufficientDataException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            log.error("Error occurred while checking object exists", e);
            Sentry.captureException(e);
            throw new MinioStorageException(e);
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404)
                return false;
            Sentry.captureException(e);
            log.error("Error occurred while checking object exists", e);
            throw new MinioStorageException(e);
        }
    }

    /**
     * Put local file into storage
     * @param bucket bucket name
     * @param object object name
     * @param file file to put
     */
    public SavedFile putFile(@NonNull String bucket, @NonNull String object, @NonNull Path file) {
        try {
            String contentType = tika.detect(file);
            ObjectWriteResponse response = client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .filename(file.toString())
                            .contentType(contentType)
                            .build()
            );
            return new SavedFile(response.etag(), contentType);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error("Error occurred while putting object", e);
            Sentry.captureException(e);
            throw new MinioStorageException(e);
        }
    }

    /**
     * Delete object from storage
     * @param bucket bucket name
     * @param object object name
     * @return <code>true</code> if objects was deleted, <code>false</code> if it does not exists before deletion
     */
    public boolean deleteObject(@NonNull String bucket, @NonNull String object) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .build()
            );
            return true;
        } catch (InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error("Error occurred while deleting object", e);
            Sentry.captureException(e);
            throw new MinioStorageException(e);
        } catch (ErrorResponseException e) {
            if (e.response().code() == 404)
                return false;
            Sentry.captureException(e);
            log.error("Error occurred while deleting object", e);
            throw new MinioStorageException(e);
        }
    }

    /**
     * Put {@link MultipartFile} into storage
     * @param bucket bucket name
     * @param object object name
     * @param file file to put
     */
    public SavedFile putMultipartFile(@NonNull String bucket, @NonNull String object, @NonNull MultipartFile file) {
        try {
            var args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .stream(file.getInputStream(), file.getSize(), 5 * 1024 * 1024);
            if (file.getContentType() != null) {
                args.contentType(file.getContentType());
            } else {
                args.contentType(tika.detect(file.getOriginalFilename()));
            }
            PutObjectArgs built = args.build();
            ObjectWriteResponse response = client.putObject(built);
            return new SavedFile(response.etag(), built.contentType());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            log.error("Error occurred while putting object", e);
            Sentry.captureException(e);
            throw new MinioStorageException(e);
        }
    }
}
