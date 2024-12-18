package com.torresj.infosas.dtos;

import java.util.List;
import java.util.Set;

public record EnrichedStaffDto(
        Long id,
        String name,
        String surname,
        String dni,
        List<StaffExamDto> staffExams,
        Set<StaffJobBankDto> staffJobBanks,
        Set<StaffSpecificJobBankDto> staffSpecificJobBanks
) {
}
