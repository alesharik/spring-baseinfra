package com.alesharik.common.openapi;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenApiAuthenticationFilter extends BasicAuthenticationFilter {
    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/docs/**");
    private final BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    public OpenApiAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager, new BasicAuthenticationEntryPoint());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!matcher.matches(request)) {
            chain.doFilter(request, response);
            return;
        }
        if (authenticationConverter.convert(request) == null) {
            var exception = new AuthenticationCredentialsNotFoundException("Docs requires authentication");
            SecurityContextHolder.clearContext();
            this.logger.debug("Failed to process authentication request", exception);
            onUnsuccessfulAuthentication(request, response, exception);
            getAuthenticationEntryPoint().commence(request, response, exception);
            return;
        }
        super.doFilterInternal(request, response, chain);
    }

    public static void setup(HttpSecurity http, OpenApiProperties properties) {
        http.addFilter(new OpenApiAuthenticationFilter(new OpenApiAuthenticationManager(properties)));
    }
}
