package com.torresj.infosas.mappers;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.ExclusionReasons;
import com.torresj.infosas.dtos.StaffExamDto;
import com.torresj.infosas.dtos.StaffJobBankDto;
import com.torresj.infosas.dtos.StaffSpecificJobBankDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    StaffExamDto toStaffExamDto(StaffExamEntity staffExamEntity);

    default StaffJobBankDto toStaffJobBankDto(StaffJobBankEntity staffJobBankEntity){
        return new StaffJobBankDto(
                staffJobBankEntity.getTreaty(),
                staffJobBankEntity.getShift(),
                staffJobBankEntity.getStatus(),
                staffJobBankEntity.getType(),
                staffJobBankEntity.getExclusionCodes() == null ?
                        null
                        : Arrays.stream(staffJobBankEntity.getExclusionCodes()
                                .split(","))
                        .map(ExclusionReasons::getExclusionReason)
                        .collect(Collectors.toList()),
                staffJobBankEntity.getExperience(),
                staffJobBankEntity.getFormation(),
                staffJobBankEntity.getOthers(),
                staffJobBankEntity.getTotal(),
                staffJobBankEntity.isProvisional(),
                staffJobBankEntity.getCutOffYear()
        );
    }

    default StaffSpecificJobBankDto toStaffSpecificJobBankDto(StaffSpecificJobBankEntity staffSpecificJobBankEntity){
        return new StaffSpecificJobBankDto(
                staffSpecificJobBankEntity.getTreaty(),
                staffSpecificJobBankEntity.getShift(),
                staffSpecificJobBankEntity.getType(),
                staffSpecificJobBankEntity.getGeneral_admission(),
                staffSpecificJobBankEntity.getSpecific_admission(),
                staffSpecificJobBankEntity.getExclusionCodes() == null ?
                        null
                        : Arrays.stream(staffSpecificJobBankEntity.getExclusionCodes()
                                .split(","))
                        .map(ExclusionReasons::getExclusionReason)
                        .collect(Collectors.toList()),
                staffSpecificJobBankEntity.getExperience(),
                staffSpecificJobBankEntity.getFormation(),
                staffSpecificJobBankEntity.getOthers(),
                staffSpecificJobBankEntity.getTotal(),
                staffSpecificJobBankEntity.isProvisional(),
                staffSpecificJobBankEntity.getCutOffYear()
        );
    }

    default EnrichedStaffExamDto toEnrichedStaffExamDto(
            StaffEntity staffEntity,
            List<StaffExamEntity> examsEntity
    ){
        return new EnrichedStaffExamDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                examsEntity.stream().map(this::toStaffExamDto).toList()
        );
    }

    default EnrichedStaffJobBankDto toEnrichedStaffJobBankDto(
            StaffEntity staffEntity,
            StaffJobBankEntity staffJobBankEntity
    ){
        return new EnrichedStaffJobBankDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                staffJobBankEntity == null ? null : toStaffJobBankDto(staffJobBankEntity)
        );
    }

    default EnrichedSpecificStaffJobBankDto toEnrichedSpecificStaffJobBankDto(
            StaffEntity staffEntity,
            StaffSpecificJobBankEntity staffJobBankEntity
    ){
        return new EnrichedSpecificStaffJobBankDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                staffJobBankEntity == null ? null : toStaffSpecificJobBankDto(staffJobBankEntity)
        );
    }
}
