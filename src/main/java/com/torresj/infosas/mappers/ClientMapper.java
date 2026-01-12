package com.torresj.infosas.mappers;

import com.torresj.infosas.dtos.*;
import com.torresj.infosas.entities.client.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toClientDto(ClientEntity clientEntity);
}
