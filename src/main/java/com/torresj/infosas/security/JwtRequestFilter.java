package com.torresj.infosas.security;

import com.torresj.infosas.services.ClientService;
import com.torresj.infosas.services.JwtService;
import com.torresj.infosas.services.impl.ClientServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClientService clientService;

    @Value("${jwt.token.prefix}")
    private final String prefix;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain
    ) throws ServletException, IOException {
        // look for Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(prefix + " ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final String name = jwtService.validateJWS(token);
        if (name == null) {
            // validation failed or token expired
            chain.doFilter(request, response);
            return;
        }

        // set user details on spring security context
        final UserDetails userDetails = ((ClientServiceImpl) clientService).loadUserByUsername(name);
        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // continue with authenticated user
        chain.doFilter(request, response);
    }
}