package com.alesharik.appversion.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class BranchNotFoundException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/app-version/branch-not-found");

    public BranchNotFoundException() {
        super(HttpStatus.NOT_FOUND);
        setType(TYPE);
        setTitle("Branch not found");
    }
}
