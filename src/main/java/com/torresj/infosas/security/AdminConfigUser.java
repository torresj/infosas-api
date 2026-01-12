package com.torresj.infosas.security;

import com.torresj.infosas.entities.client.ClientEntity;
import com.torresj.infosas.repositories.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.torresj.infosas.enums.Role.ROLE_ADMIN;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminConfigUser {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Value("${admin.id}")
    private final long adminId;

    @Value("${admin.password}")
    private final String adminPassword;

    @Bean
    ClientEntity createAdminClient() {
        var client = clientRepository.findById(adminId);
        client.ifPresent(clientRepository::delete);
        var user = clientRepository.save(
                ClientEntity.builder()
                        .id(adminId)
                        .role(ROLE_ADMIN)
                        .name("admin")
                        .password(encoder.encode(adminPassword))
                        .build());
        log.info("Created admin user: {}", user);
        return user;
    }
}