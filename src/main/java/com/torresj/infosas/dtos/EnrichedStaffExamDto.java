package com.torresj.infosas.dtos;

import java.util.List;

public record EnrichedStaffExamDto(
        Long id,
        String name,
        String surname,
        String dni,
        List<StaffExamDto> exams
) {
}
