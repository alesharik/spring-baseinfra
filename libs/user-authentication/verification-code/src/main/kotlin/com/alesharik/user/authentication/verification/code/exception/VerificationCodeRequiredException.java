package com.alesharik.user.authentication.verification.code.exception;

import lombok.RequiredArgsConstructor;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class VerificationCodeRequiredException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-required");
    private final Duration timeout;

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Verification code required";
    }

    @Override
    public String getDetail() {
        return "The code was sent to you. You should provide it to auth handler";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of("timeoutSeconds", timeout.toSeconds());
    }
}
