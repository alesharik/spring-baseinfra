package com.alesharik.spring.file.storage.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

/**
 * This exception is thrown if file does not satisfy some constraints. As example, if JPEG file is required, all other
 * formats will receive this exception
 */
public class FileConversionFailedException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://docs.alesharik.com/docs/error/file-storage/file-conversion-failed");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "File conversion failed";
    }

    @Override
    public String getDetail() {
        return "File has unsupported format or bad content";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }
}
