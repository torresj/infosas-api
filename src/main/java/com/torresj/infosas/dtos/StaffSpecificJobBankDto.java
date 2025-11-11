package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.SasSubType;
import com.torresj.infosas.enums.Status;

import java.util.List;

public record StaffSpecificJobBankDto(
        String treaty,
        String shift,
        SasSubType type,
        Status general_admission,
        Status specific_admission,
        List<ExclusionReasonDto> exclusionReasons,
        String experience,
        String formation,
        String others,
        String total,
        boolean provisional,
        int cutOffYear
) {
}
