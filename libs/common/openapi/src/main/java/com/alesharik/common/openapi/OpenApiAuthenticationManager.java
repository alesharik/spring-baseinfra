package com.alesharik.common.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class OpenApiAuthenticationManager implements AuthenticationManager {
    public final OpenApiProperties openApiProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String login = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            if (!openApiProperties.getUsername().equals(login))
                throw new UsernameNotFoundException("Username not found");
            if (!openApiProperties.getPassword().equals(password))
                throw new BadCredentialsException("Password does not match");
            return new OpenApiAuthentication();
        } else {
            throw new BadCredentialsException("Docs requires basic authentication");
        }
    }
}
