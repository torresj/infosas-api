package com.torresj.infosas.services.impl;

import com.torresj.infosas.dtos.AuthResponseDto;
import com.torresj.infosas.exceptions.InfoSasAuthenticationException;
import com.torresj.infosas.repositories.client.ClientRepository;
import com.torresj.infosas.services.AuthService;
import com.torresj.infosas.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto authenticate(long id, String password) {
        log.debug("[AUTH SERVICE] Authenticating client");
        var client = clientRepository.findById(id).orElseThrow(InfoSasAuthenticationException::new);
        if (!encoder.matches(password, client.getPassword())) {
            throw new InfoSasAuthenticationException();
        }
        log.debug("[AUTH SERVICE] Client name: {}", client.getName());
        return new AuthResponseDto(jwtService.createJWS(client.getName()));
    }
}
