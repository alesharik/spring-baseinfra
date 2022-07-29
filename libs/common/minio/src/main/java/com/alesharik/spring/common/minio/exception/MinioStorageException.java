package com.alesharik.spring.common.minio.exception;

import lombok.RequiredArgsConstructor;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

@RequiredArgsConstructor
public class MinioStorageException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/storage/minio/internal-error");
    private final Exception exception;

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Minio internal error";
    }

    @Override
    public String getMessage() {
        return exception.getMessage();
    }

    @Override
    public StatusType getStatus() {
        return Status.INTERNAL_SERVER_ERROR;
    }
}
