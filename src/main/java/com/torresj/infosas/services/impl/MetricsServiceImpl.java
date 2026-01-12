package com.torresj.infosas.services.impl;

import com.torresj.infosas.dtos.MetricsDto;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.repositories.staff.StaffExamRepository;
import com.torresj.infosas.repositories.staff.StaffJobBankRepository;
import com.torresj.infosas.repositories.staff.StaffRepository;
import com.torresj.infosas.repositories.staff.StaffSpecificJobBankRepository;
import com.torresj.infosas.services.MetricsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private final StaffRepository staffRepository;
    private final StaffJobBankRepository staffJobBankRepository;
    private final StaffSpecificJobBankRepository staffSpecificJobBankRepository;
    private final StaffExamRepository staffExamRepository;

    @Override
    public MetricsDto getMetrics() {
        var totalStaff = staffRepository.count();
        var totalStaffExam = staffExamRepository.count();
        var totalJobBank = staffJobBankRepository.count();
        var totalSpecificJobBank = staffSpecificJobBankRepository.count();
        return new MetricsDto(
                StaffType.values().length,
                totalStaff,
                totalStaffExam,
                totalJobBank,
                totalSpecificJobBank,
                totalJobBank + totalSpecificJobBank
        );
    }
}
