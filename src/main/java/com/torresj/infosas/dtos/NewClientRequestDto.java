package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.Role;

public record NewClientRequestDto(long id, String name, String password, Role role) {
}
