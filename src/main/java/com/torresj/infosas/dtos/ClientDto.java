package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.Role;

public record ClientDto(long id, String name, Role role) {
}
