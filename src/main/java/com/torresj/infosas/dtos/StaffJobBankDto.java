package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.Status;

import java.util.List;

public record StaffJobBankDto(
        String treaty,
        String shift,
        Status status,
        JobBankType type,
        List<ExclusionReasonDto> exclusionReasons,
        String experience,
        String formation,
        String others,
        String total,
        boolean provisional,
        int cutOffYear
) {
}
