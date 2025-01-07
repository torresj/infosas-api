package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.StaffType;

public record StaffDto(
        Long id,
        String name,
        String surname,
        String dni,
        StaffType type,
        int exams,
        int jobBanks,
        int specificJobBanks
) {
}
