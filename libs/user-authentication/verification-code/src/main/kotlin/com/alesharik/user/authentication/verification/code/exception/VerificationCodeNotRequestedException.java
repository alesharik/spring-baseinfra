package com.alesharik.user.authentication.verification.code.exception;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;

public class VerificationCodeNotRequestedException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-not-requested");

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Verification code not requested";
    }

    @Override
    public String getDetail() {
        return "You should request verification code from your auth handler";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }
}
