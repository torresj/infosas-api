package com.torresj.infosas.services.impl;

import com.torresj.infosas.dtos.ClientDto;
import com.torresj.infosas.entities.client.ClientEntity;
import com.torresj.infosas.enums.Role;
import com.torresj.infosas.exceptions.ClientNotFoundException;
import com.torresj.infosas.mappers.ClientMapper;
import com.torresj.infosas.repositories.client.ClientRepository;
import com.torresj.infosas.security.CustomUserDetails;
import com.torresj.infosas.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService, UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto get(long id) {
        return clientMapper.toClientDto(
                clientRepository.findById(id).orElseThrow(
                        () -> new ClientNotFoundException(id)
                )
        );
    }

    @Override
    public List<ClientDto> get() {
        return clientRepository.findAll().stream().map(clientMapper::toClientDto).toList();
    }

    @Override
    public ClientDto save(long id, String name, String password, Role role) {
        var client = clientRepository.save(ClientEntity.builder()
                .id(id)
                .name(name)
                .password(password)
                .role(role)
                .build()
        );

        return clientMapper.toClientDto(client);
    }

    @Override
    public void delete(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String clientName) throws UsernameNotFoundException {
        ClientEntity client = clientRepository.findByName(clientName)
                .orElseThrow(() -> new UsernameNotFoundException(clientName));
        return new CustomUserDetails(client);
    }
}
