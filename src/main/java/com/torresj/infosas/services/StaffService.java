package com.torresj.infosas.services;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.StaffExamType;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.exceptions.StaffNotFoundException;

import java.util.List;
import java.util.Set;

public interface StaffService {
    Set<StaffDto> getStaffsBySurname(String surname);
    Set<StaffDto> getStaffsByDni(String dni);
    Set<StaffDto> getStaffs(String name, String surname, StaffType type);
    EnrichedStaffDto getStaffById(Long id) throws StaffNotFoundException;
    List<EnrichedStaffExamDto> getEnrichedStaffExam(String surname, StaffExamType type);
    Set<EnrichedStaffJobBankDto> getEnrichedStaffJobBank(String surname, JobBankType type);
    Set<EnrichedSpecificStaffJobBankDto> getEnrichedSpecificStaffJobBank(String surname, SpecificJobBankType type);
}
