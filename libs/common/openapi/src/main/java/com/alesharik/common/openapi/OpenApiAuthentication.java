package com.alesharik.common.openapi;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class OpenApiAuthentication implements Authentication {
    private final Collection<? extends GrantedAuthority> authorities = List.of(() -> "ROLE_DOCS");
    private final String principal = "docs";

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return principal;
    }
}
