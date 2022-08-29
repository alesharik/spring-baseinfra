package com.alesharik.user.authentication.verification.code.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

public class VerificationSentTooFastException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-sent-too-fast");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Verification code sent request too fast";
    }

    @Override
    public String getDetail() {
        return "Slow down and wait before sending another request";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }
}
