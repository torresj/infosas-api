package com.torresj.infosas.services;

import com.torresj.infosas.dtos.AuthResponseDto;

public interface AuthService {
    AuthResponseDto authenticate(long id, String password);
}
