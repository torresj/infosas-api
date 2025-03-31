package com.torresj.infosas.dtos;

import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.Status;

import java.util.List;

public record StaffSpecificJobBankDto(
        String treaty,
        String shift,
        SpecificJobBankType type,
        Status general_admission,
        Status specific_admission,
        List<ExclusionReasonDto> exclusionReasons,
        String experience,
        String formation,
        String others,
        String total,
        boolean provisional,
        Integer cutOffYear
) {
}
