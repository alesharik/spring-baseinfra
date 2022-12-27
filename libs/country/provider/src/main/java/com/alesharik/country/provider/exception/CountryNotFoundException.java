package com.alesharik.country.provider.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

/**
 * This exception is thrown when country not found
 */
public class CountryNotFoundException extends ErrorResponseException {
    public static final URI TYPE = URI.create("https://api.nplone.com/docs/error/country/country-not-found");
    @Getter
    private final String code;

    public CountryNotFoundException(String code) {
        super(HttpStatus.BAD_REQUEST);
        this.code = code;
        setType(TYPE);
        setTitle("Country not found");
        setDetail("Country not found for code " + code);
        getBody().setProperty("code", code);
    }
}
