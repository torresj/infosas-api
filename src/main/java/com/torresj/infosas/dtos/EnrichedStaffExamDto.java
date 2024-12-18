package com.torresj.infosas.dtos;

public record EnrichedStaffExamDto(
        Long id,
        String name,
        String surname,
        String dni,
        StaffExamDto provisionalExam,
        StaffExamDto definitiveExam
) {
}
