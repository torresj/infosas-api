package com.torresj.infosas.services.impl;

import com.torresj.infosas.dtos.MetricsDto;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.repositories.StaffExamRepository;
import com.torresj.infosas.repositories.StaffJobBankRepository;
import com.torresj.infosas.repositories.StaffRepository;
import com.torresj.infosas.repositories.StaffSpecificJobBankRepository;
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
        return new MetricsDto(
                StaffType.values().length,
                staffRepository.count(),
                staffExamRepository.count(),
                staffJobBankRepository.count(),
                staffSpecificJobBankRepository.count()
        );
    }
}
