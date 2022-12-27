package com.alesharik.appversion.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class ClientNotFoundException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://spring.alesharik.com/docs/error/app-version/client-not-found");

    public ClientNotFoundException() {
        super(HttpStatus.NOT_FOUND);
        setType(TYPE);
        setTitle("Client not found");
    }
}

