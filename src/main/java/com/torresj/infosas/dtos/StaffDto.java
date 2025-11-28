package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.StaffType;

import java.util.List;

public record StaffDto(
        Long id,
        String name,
        String surname,
        String dni,
        List<StaffType> types,
        int exams,
        int jobBanks,
        int specificJobBanks
) {
}
