package com.torresj.infosas.dtos;

public record EnrichedSpecificStaffJobBankDto(
        Long id,
        String name,
        String surname,
        String dni,
        StaffSpecificJobBankDto staffJobBank
) {
}
