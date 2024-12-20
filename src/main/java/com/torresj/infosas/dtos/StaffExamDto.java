package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.StaffExamType;

public record StaffExamDto(StaffExamType type, String shift, boolean provisional, float total, float op, float con, int position ) {
}
