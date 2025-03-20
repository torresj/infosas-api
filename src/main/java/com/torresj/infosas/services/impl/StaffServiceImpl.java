package com.torresj.infosas.services.impl;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.QueueMessage;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.MessageType;
import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.exceptions.StaffNotFoundException;
import com.torresj.infosas.mappers.StaffMapper;
import com.torresj.infosas.repositories.StaffExamRepository;
import com.torresj.infosas.repositories.StaffJobBankRepository;
import com.torresj.infosas.repositories.StaffRepository;
import com.torresj.infosas.repositories.StaffSpecificJobBankRepository;
import com.torresj.infosas.services.ProducerService;
import com.torresj.infosas.services.StaffService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Slf4j
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {

    private static final int MAX_NUMBER_OF_STAFFS = 100;

    private final StaffRepository staffRepository;
    private final StaffJobBankRepository staffJobBankRepository;
    private final StaffSpecificJobBankRepository staffSpecificJobBankRepository;
    private final StaffExamRepository staffExamRepository;
    private final StaffMapper staffMapper;
    private final ProducerService producerService;

    @Value("${telegram.notification.channel.id}")
    private final String notificationChannelId;

    @Override
    public Set<StaffDto> getStaffsBySurname(String surname) {
        return staffRepository.findAllBySurnameContainingIgnoreCase(surname, Limit.of(MAX_NUMBER_OF_STAFFS))
                .stream()
                .map(entity -> {
                    int exams = staffExamRepository.findByStaffId(entity.getId()).size();
                    int jobBanks = staffJobBankRepository.findByStaffId(entity.getId()).size();
                    int specificJobBanks = staffSpecificJobBankRepository.findByStaffId(entity.getId()).size();
                    return toStaffDto(entity, exams, jobBanks, specificJobBanks);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<StaffDto> getStaffsByDni(String dni) {
        return staffRepository.findAllByDni(dni, Limit.of(MAX_NUMBER_OF_STAFFS))
                .stream()
                .map(entity -> {
                    int exams = staffExamRepository.findByStaffId(entity.getId()).size();
                    int jobBanks = staffJobBankRepository.findByStaffId(entity.getId()).size();
                    int specificJobBanks = staffSpecificJobBankRepository.findByStaffId(entity.getId()).size();
                    return toStaffDto(entity, exams, jobBanks, specificJobBanks);
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<StaffDto> getStaffs(String name, String surname, StaffType type) {
        Set<StaffDto> staffs;
        if(isBlank(name) && type == null) {
            staffs = getStaffsBySurname(surname.trim());
        }else if(!isBlank(name) && type == null) {
            staffs = staffRepository.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name.trim(), surname.trim(), Limit.of(MAX_NUMBER_OF_STAFFS))
                    .stream()
                    .map(entity -> {
                        int exams = staffExamRepository.findByStaffId(entity.getId()).size();
                        int jobBanks = staffJobBankRepository.findByStaffId(entity.getId()).size();
                        int specificJobBanks = staffSpecificJobBankRepository.findByStaffId(entity.getId()).size();
                        return toStaffDto(entity, exams, jobBanks, specificJobBanks);
                    })
                    .collect(Collectors.toSet());
        } else if(isBlank(name) && type != null) {
            staffs = staffRepository.findAllBySurnameContainingIgnoreCaseAndType(surname.trim(), type, Limit.of(MAX_NUMBER_OF_STAFFS))
                    .stream()
                    .map(entity -> {
                        int exams = staffExamRepository.findByStaffId(entity.getId()).size();
                        int jobBanks = staffJobBankRepository.findByStaffId(entity.getId()).size();
                        int specificJobBanks = staffSpecificJobBankRepository.findByStaffId(entity.getId()).size();
                        return toStaffDto(entity, exams, jobBanks, specificJobBanks);
                    })
                    .collect(Collectors.toSet());
        } else {
            staffs = staffRepository.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndType(name.trim(), surname.trim(), type,  Limit.of(MAX_NUMBER_OF_STAFFS))
                    .stream()
                    .map(entity -> {
                        int exams = staffExamRepository.findByStaffId(entity.getId()).size();
                        int jobBanks = staffJobBankRepository.findByStaffId(entity.getId()).size();
                        int specificJobBanks = staffSpecificJobBankRepository.findByStaffId(entity.getId()).size();
                        return toStaffDto(entity, exams, jobBanks, specificJobBanks);
                    })
                    .collect(Collectors.toSet());
        }

        if (staffs.isEmpty()) {
            producerService.sendMessage(QueueMessage.builder()
                    .text("No se ha encontrado a ningún profesional para la búsqueda con nombre: " + name + ", apellidos: " + surname + " y categoria profesional " + type)
                    .chatId(notificationChannelId)
                    .type(MessageType.WARNING)
                    .build());
        }
        return staffs;
    }

    @Override
    public EnrichedStaffDto getStaffById(Long id) throws StaffNotFoundException {
        var entity = staffRepository.findById(id).orElseThrow(() -> new StaffNotFoundException(id));
        return toEnrichedStaffDto(entity);
    }

    @Override
    public List<EnrichedStaffExamDto> getEnrichedStaffExam(String surname) {
        return staffRepository.findAllBySurnameContainingIgnoreCase(surname, Limit.of(MAX_NUMBER_OF_STAFFS))
                .stream()
                .map(staff -> {
                    var staffExam = staffExamRepository.findByStaffId(
                            staff.getId()
                    );
                    return staffMapper.toEnrichedStaffExamDto(staff, staffExam);
                })
                .filter(enrichedStaffExamDto -> !enrichedStaffExamDto.exams().isEmpty())
                .toList();
    }

    @Override
    public Set<EnrichedStaffJobBankDto> getEnrichedStaffJobBank(String surname, JobBankType type) {
        return staffRepository.findAllBySurnameContainingIgnoreCase(surname, Limit.of(MAX_NUMBER_OF_STAFFS))
                .stream()
                .map(staffEntity -> {
                    var staffJobBankEntity = staffJobBankRepository.findByStaffIdAndType(staffEntity.getId(), type);
                    return staffMapper.toEnrichedStaffJobBankDto(staffEntity, staffJobBankEntity);
                })
                .filter(enrichedStaffJobBankDto -> enrichedStaffJobBankDto.staffJobBank() != null)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EnrichedSpecificStaffJobBankDto> getEnrichedSpecificStaffJobBank(String surname, SpecificJobBankType type) {
        return staffRepository.findAllBySurnameContainingIgnoreCase(surname, Limit.of(MAX_NUMBER_OF_STAFFS))
                .stream()
                .map(staffEntity -> {
                    var staffJobBankEntity = staffSpecificJobBankRepository.findByStaffIdAndType(staffEntity.getId(), type);
                    return staffMapper.toEnrichedSpecificStaffJobBankDto(staffEntity, staffJobBankEntity);
                })
                .filter(enrichedStaffJobBankDto -> enrichedStaffJobBankDto.staffJobBank() != null)
                .collect(Collectors.toSet());
    }

    private StaffDto toStaffDto(StaffEntity staffEntity, int exams, int jobBanks, int specificJobBanks) {
        return new StaffDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                staffEntity.getType(),
                exams,
                jobBanks,
                specificJobBanks
        );
    }

    private EnrichedStaffDto toEnrichedStaffDto(StaffEntity staffEntity) {
        return new EnrichedStaffDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                staffExamRepository.findByStaffId(staffEntity.getId())
                        .stream().
                        map(staffMapper::toStaffExamDto).toList(),
                staffJobBankRepository.findByStaffId(staffEntity.getId())
                        .stream()
                        .map(staffMapper::toStaffJobBankDto).collect(Collectors.toSet()),
                staffSpecificJobBankRepository.findByStaffId(staffEntity.getId())
                        .stream()
                        .map(staffMapper::toStaffSpecificJobBankDto).collect(Collectors.toSet())
        );
    }
}
