package com.torresj.infosas.dtos;

public record MetricsDto(
    long typesOfStaff,
    long staffs,
    long exams,
    long jobBanks,
    long specificJobBanks,
    long totalJobBanks
) {
}
