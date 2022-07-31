package com.alesharik.appversion.service.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

public class ClientNotFoundException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/app-version/client-not-found");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Client not found";
    }

    @Override
    public StatusType getStatus() {
        return Status.NOT_FOUND;
    }
}

