package com.alesharik.user.authentication.verification.code.exception;

import lombok.RequiredArgsConstructor;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
public class VerificationCodeInvalidException extends ThrowableProblem {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/auth/verification-code-invalid");
    private final boolean codeReset;

    @Override
    public URI getType() {
        return TYPE;
    }

    @Override
    public String getTitle() {
        return "Verification code invalid";
    }

    @Override
    public String getDetail() {
        if (codeReset)
            return "You typed in wrong email verification code. Code was reset";
        else
            return "You typed in wrong email verification code";
    }

    @Override
    public StatusType getStatus() {
        return Status.BAD_REQUEST;
    }

    @Override
    public Map<String, Object> getParameters() {
        return Map.of("codeReset", codeReset);
    }
}
