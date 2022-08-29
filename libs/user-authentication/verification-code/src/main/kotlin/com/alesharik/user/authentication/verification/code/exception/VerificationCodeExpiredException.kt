package com.alesharik.user.authentication.verification.code.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

public class VerificationCodeExpiredException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-expired");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Verification code expired";
    }

    @Override
    public String getDetail() {
        return "You should send another verification code request";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }
}
