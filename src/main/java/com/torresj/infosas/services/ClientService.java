package com.torresj.infosas.services;

import com.torresj.infosas.dtos.ClientDto;
import com.torresj.infosas.enums.Role;

import java.util.List;

public interface ClientService {
    ClientDto get(long id);
    List<ClientDto> get();
    ClientDto save(long id, String name, String password, Role role);
    void delete(long id);
}
