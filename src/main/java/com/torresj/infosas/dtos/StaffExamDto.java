package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.SasSubType;

public record StaffExamDto(
        SasSubType type,
        String shift,
        boolean provisional,
        float total,
        float op,
        float con,
        int position,
        int examYear
) {
}
