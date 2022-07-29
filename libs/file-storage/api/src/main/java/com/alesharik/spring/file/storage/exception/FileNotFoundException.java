package com.alesharik.spring.file.storage.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

/**
 * This exception is thrown when requested file does not exist
 */
public class FileNotFoundException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://docs.alesharik.com/docs/error/file-storage/file-not-found");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "File not found";
    }

    @Override
    public StatusType getStatus() {
        return Status.NOT_FOUND;
    }
}
