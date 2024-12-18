package com.torresj.infosas.dtos;

public record EnrichedStaffJobBankDto(
        Long id,
        String name,
        String surname,
        String dni,
        StaffJobBankDto staffJobBank
) {
}
