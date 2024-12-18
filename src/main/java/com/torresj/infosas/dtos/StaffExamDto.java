package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.StaffType;

public record StaffExamDto(StaffType type, String shift, boolean provisional, float total, float op, float con, int position ) {
}
