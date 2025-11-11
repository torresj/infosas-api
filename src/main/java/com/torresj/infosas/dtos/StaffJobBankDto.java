package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.SasSubType;
import com.torresj.infosas.enums.Status;

import java.util.List;

public record StaffJobBankDto(
        String treaty,
        String shift,
        Status status,
        SasSubType type,
        List<ExclusionReasonDto> exclusionReasons,
        String experience,
        String formation,
        String others,
        String total,
        boolean provisional,
        int cutOffYear
) {
}
