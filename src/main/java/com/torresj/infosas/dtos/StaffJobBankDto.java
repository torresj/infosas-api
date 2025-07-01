package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.Status;

public record StaffJobBankDto(
        String treaty,
        String shift,
        Status status,
        JobBankType type,
        ExclusionReasonDto exclusionReason,
        String experience,
        String formation,
        String others,
        String total,
        boolean provisional,
        int cutOffYear
) {
}
