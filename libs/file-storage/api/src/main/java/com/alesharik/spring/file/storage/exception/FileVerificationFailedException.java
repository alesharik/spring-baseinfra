package com.alesharik.spring.file.storage.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

/**
 * This exception is thrown if file does not satisfy some constraints. As example, if JPEG file is required, all other
 * formats will receive this exception
 */
public class FileVerificationFailedException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://docs.alesharik.com/docs/error/file-storage/file-verification-failed");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "File verification failed";
    }

    @Override
    public String getDetail() {
        return "File has wrong extension or contents";
    }

    @Override
    public StatusType getStatus() {
        return Status.CONFLICT;
    }
}
