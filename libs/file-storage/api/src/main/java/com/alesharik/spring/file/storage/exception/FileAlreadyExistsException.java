package com.alesharik.spring.file.storage.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

/**
 * This exception is thrown when file already has been added
 */
public class FileAlreadyExistsException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/file-storage/file-already-exists");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "File already exists";
    }

    @Override
    public StatusType getStatus() {
        return Status.CONFLICT;
    }
}
