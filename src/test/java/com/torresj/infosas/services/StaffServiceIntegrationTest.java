package com.torresj.infosas.services;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import com.torresj.infosas.enums.SasSubType;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.repositories.StaffExamRepository;
import com.torresj.infosas.repositories.StaffJobBankRepository;
import com.torresj.infosas.repositories.StaffRepository;
import com.torresj.infosas.repositories.StaffSpecificJobBankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static com.torresj.infosas.enums.SasSubType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaffServiceIntegrationTest {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffExamRepository staffExamRepository;

    @Autowired
    private StaffSpecificJobBankRepository staffSpecificJobBankRepository;

    @Autowired
    private StaffJobBankRepository staffJobBankRepository;

    @Autowired
    private StaffService staffService;

    @Test
    void givenStaffsWhenGetStaffBySurnameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("name")
                        .surname("surname")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("name2")
                        .surname("test22")
                        .dni("dni2")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("name3")
                        .surname("test23")
                        .dni("dni3")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        staffExamRepository.save(givenStaffExam(entitesList.get(1).getId(), NURSE_EXAM, false));
        staffJobBankRepository.save(givenStaffJobBank(entitesList.get(1).getId(), TCAE_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(1).getId(),NURSE_CRITICS_SPECIFIC_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(1).getId(),NURSE_DIALYSIS_SPECIFIC_JOB_BANK));

        //when
        Set<StaffDto> staffs = staffService.getStaffsBySurname("test2");

        //then
        assertThat(staffs).hasSize(2);
    }

    @Test
    void givenStaffsWhenGetStaffByNameAndSurnameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name")
                        .surname("test-surname")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name2")
                        .surname("test-surname2")
                        .dni("dni2")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name3")
                        .surname("test-surname3")
                        .dni("dni3")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        staffExamRepository.save(givenStaffExam(entitesList.get(2).getId(), NURSE_EXAM, false));
        staffJobBankRepository.save(givenStaffJobBank(entitesList.get(2).getId(), NURSE_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(2).getId(),NURSE_CRITICS_SPECIFIC_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(2).getId(),NURSE_DIALYSIS_SPECIFIC_JOB_BANK));

        //when
        Set<StaffDto> staffs = staffService.getStaffs("name3", "surname3", null);

        //then
        assertThat(staffs).hasSize(1);
    }

    @Test
    void givenStaffsWhenGetStaffByTypeAndSurnameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name4")
                        .surname("test-surname4")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name5")
                        .surname("test-surname5")
                        .dni("dni2")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name6")
                        .surname("test-surname6")
                        .dni("dni3")
                        .types(List.of(StaffType.FISIO))
                        .build()
        );

        staffRepository.saveAll(entities);

        //when
        Set<StaffDto> staffs = staffService.getStaffs(null, "test-surname", StaffType.NURSE);

        //then
        assertThat(staffs).hasSize(2);
    }

    @Test
    void givenStaffsWhenGetStaffByTypeAndSurnameAndNameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name7")
                        .surname("test-surname7")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name8")
                        .surname("test-surname8")
                        .dni("dni2")
                        .types(List.of(StaffType.NURSE))
                        .build(),
                StaffEntity.builder()
                        .name("test-name9")
                        .surname("test-surname9")
                        .dni("dni3")
                        .types(List.of(StaffType.OCCUPATIONAL_THERAPY))
                        .build()
        );

        staffRepository.saveAll(entities);

        //when
        Set<StaffDto> staffs = staffService.getStaffs("test-name", "test-surname", StaffType.OCCUPATIONAL_THERAPY);

        //then
        assertThat(staffs).hasSize(1);
    }

    @Test
    void givenStaffWhenGetStaffByIdThenReturnStaff() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("surnameIt")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );

        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE_EXAM, false));
        staffJobBankRepository.save(givenStaffJobBank(entity.getId(), TCAE_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(),NURSE_CRITICS_SPECIFIC_JOB_BANK));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(),NURSE_DIALYSIS_SPECIFIC_JOB_BANK));

        //when
        EnrichedStaffDto staff = staffService.getStaffById(entity.getId());

        //then
        assertThat(staff).isNotNull();
        assertThat(staff.id()).isEqualTo(entity.getId());
        assertThat(staff.surname()).isEqualTo(entity.getSurname());
        assertThat(staff.name()).isEqualTo(entity.getName());
        assertThat(staff.dni()).isEqualTo(entity.getDni());
        assertThat(staff.staffSpecificJobBanks()).hasSize(2);
        assertThat(staff.staffExams()).hasSize(1);
        assertThat(staff.staffJobBanks()).hasSize(1);
    }

    @Test
    void givenStaffWithExamsWhenGetStaffsBySurnameThenReturnStaffWithExams() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithExams")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );

        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE_EXAM, true));
        staffExamRepository.save(givenStaffExam(entity.getId(), TCAE_EXAM, false));

        //when
        List<EnrichedStaffExamDto> staffs = staffService.getEnrichedStaffExam("staffWithExams");

        //then
        assertThat(staffs).isNotNull();
        assertThat(staffs).hasSize(1);
        assertThat(staffs.getFirst().id()).isEqualTo(entity.getId());
        assertThat(staffs.getFirst().surname()).isEqualTo(entity.getSurname());
        assertThat(staffs.getFirst().name()).isEqualTo(entity.getName());
        assertThat(staffs.getFirst().dni()).isEqualTo(entity.getDni());
        assertThat(staffs.getFirst().exams()).hasSize(2);
        assertThat(staffs.getFirst().exams().getFirst().provisional()).isTrue();
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE_EXAM);
        assertThat(staffs.getFirst().exams().get(1).provisional()).isFalse();
        assertThat(staffs.getFirst().exams().get(1).type()).isEqualTo(TCAE_EXAM);
    }

    @Test
    void givenStaffWithProvisionalExamsWhenGetStaffsBySurnameAndTypeThenReturnStaffWithExam() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithProvisionalExam")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );
        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE_EXAM, true));

        //when
        List<EnrichedStaffExamDto> staffs = staffService.getEnrichedStaffExam("staffWithProvisionalExam");

        //then
        assertThat(staffs).isNotNull();
        assertThat(staffs).hasSize(1);
        assertThat(staffs.getFirst().id()).isEqualTo(entity.getId());
        assertThat(staffs.getFirst().surname()).isEqualTo(entity.getSurname());
        assertThat(staffs.getFirst().name()).isEqualTo(entity.getName());
        assertThat(staffs.getFirst().dni()).isEqualTo(entity.getDni());
        assertThat(staffs.getFirst().exams()).hasSize(1);
        assertThat(staffs.getFirst().exams().getFirst().provisional()).isTrue();
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE_EXAM);
    }

    @Test
    void givenStaffWithDefinitiveExamsWhenGetStaffsBySurnameAndTypeThenReturnStaffWithExam() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithDefinitiveExam")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );
        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE_EXAM, false));

        //when
        List<EnrichedStaffExamDto> staffs = staffService.getEnrichedStaffExam("staffWithDefinitiveExam");

        //then
        assertThat(staffs).isNotNull();
        assertThat(staffs).hasSize(1);
        assertThat(staffs.getFirst().id()).isEqualTo(entity.getId());
        assertThat(staffs.getFirst().surname()).isEqualTo(entity.getSurname());
        assertThat(staffs.getFirst().name()).isEqualTo(entity.getName());
        assertThat(staffs.getFirst().dni()).isEqualTo(entity.getDni());
        assertThat(staffs.getFirst().exams()).hasSize(1);
        assertThat(staffs.getFirst().exams().getFirst().provisional()).isFalse();
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE_EXAM);
    }

    @Test
    void givenStaffWithJobBankWhenGetStaffsBySurnameAndTypeThenReturnStaffWithJobBank() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithJobBank")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );
        var staffJobBankEntity = staffJobBankRepository.save(givenStaffJobBank(entity.getId(), TCAE_JOB_BANK));

        //when
        Set<EnrichedStaffJobBankDto> staffs = staffService.getEnrichedStaffJobBank("staffWithJobBank", TCAE_JOB_BANK);

        //then
        assertThat(staffs).isNotNull();
        assertThat(staffs).hasSize(1);

        EnrichedStaffJobBankDto enrichedStaffJobBankDto = staffs.stream().findFirst().get();

        assertThat(enrichedStaffJobBankDto).isNotNull();
        assertThat(enrichedStaffJobBankDto.id()).isEqualTo(entity.getId());
        assertThat(enrichedStaffJobBankDto.surname()).isEqualTo(entity.getSurname());
        assertThat(enrichedStaffJobBankDto.name()).isEqualTo(entity.getName());
        assertThat(enrichedStaffJobBankDto.dni()).isEqualTo(entity.getDni());
        assertThat(enrichedStaffJobBankDto.staffJobBank()).isNotNull();
        assertThat(enrichedStaffJobBankDto.staffJobBank().shift()).isEqualTo(staffJobBankEntity.getShift());
        assertThat(enrichedStaffJobBankDto.staffJobBank().treaty()).isEqualTo(staffJobBankEntity.getTreaty());
        assertThat(enrichedStaffJobBankDto.staffJobBank().total()).isEqualTo(staffJobBankEntity.getTotal());
        assertThat(enrichedStaffJobBankDto.staffJobBank().formation()).isEqualTo(staffJobBankEntity.getFormation());
        assertThat(enrichedStaffJobBankDto.staffJobBank().status()).isEqualTo(staffJobBankEntity.getStatus());
        assertThat(enrichedStaffJobBankDto.staffJobBank().type()).isEqualTo(staffJobBankEntity.getType());
        assertThat(enrichedStaffJobBankDto.staffJobBank().others()).isEqualTo(staffJobBankEntity.getOthers());
        assertThat(enrichedStaffJobBankDto.staffJobBank().experience()).isEqualTo(staffJobBankEntity.getExperience());
    }

    @Test
    void givenStaffWithSpecificJobBankWhenGetStaffsBySurnameAndTypeThenReturnStaffWithJobBank() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithSpecificJobBank")
                        .name("name")
                        .dni("dni")
                        .types(List.of(StaffType.NURSE))
                        .build()
        );
        var staffJobBankEntity = staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(), NURSE_CRITICS_SPECIFIC_JOB_BANK));

        //when
        Set<EnrichedSpecificStaffJobBankDto> staffs = staffService.getEnrichedSpecificStaffJobBank(
                "staffWithSpecificJobBank",
                NURSE_CRITICS_SPECIFIC_JOB_BANK
        );

        //then
        assertThat(staffs).isNotNull();
        assertThat(staffs).hasSize(1);

        EnrichedSpecificStaffJobBankDto enrichedStaffJobBankDto = staffs.stream().findFirst().get();

        assertThat(enrichedStaffJobBankDto).isNotNull();
        assertThat(enrichedStaffJobBankDto.id()).isEqualTo(entity.getId());
        assertThat(enrichedStaffJobBankDto.surname()).isEqualTo(entity.getSurname());
        assertThat(enrichedStaffJobBankDto.name()).isEqualTo(entity.getName());
        assertThat(enrichedStaffJobBankDto.dni()).isEqualTo(entity.getDni());
        assertThat(enrichedStaffJobBankDto.staffJobBank()).isNotNull();
        assertThat(enrichedStaffJobBankDto.staffJobBank().shift()).isEqualTo(staffJobBankEntity.getShift());
        assertThat(enrichedStaffJobBankDto.staffJobBank().treaty()).isEqualTo(staffJobBankEntity.getTreaty());
        assertThat(enrichedStaffJobBankDto.staffJobBank().total()).isEqualTo(staffJobBankEntity.getTotal());
        assertThat(enrichedStaffJobBankDto.staffJobBank().formation()).isEqualTo(staffJobBankEntity.getFormation());
        assertThat(enrichedStaffJobBankDto.staffJobBank().specific_admission()).isEqualTo(staffJobBankEntity.getSpecific_admission());
        assertThat(enrichedStaffJobBankDto.staffJobBank().general_admission()).isEqualTo(staffJobBankEntity.getGeneral_admission());
        assertThat(enrichedStaffJobBankDto.staffJobBank().type()).isEqualTo(staffJobBankEntity.getType());
        assertThat(enrichedStaffJobBankDto.staffJobBank().others()).isEqualTo(staffJobBankEntity.getOthers());
        assertThat(enrichedStaffJobBankDto.staffJobBank().experience()).isEqualTo(staffJobBankEntity.getExperience());
    }

    private StaffExamEntity givenStaffExam(Long staffId, SasSubType type, boolean provisional) {
        return StaffExamEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .provisional(provisional)
                .shift("L")
                .build();
    }

    private StaffJobBankEntity givenStaffJobBank(Long staffId, SasSubType type) {
        return StaffJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .shift("L")
                .build();
    }

    private StaffSpecificJobBankEntity givenStaffSpecificJobBank(Long staffId, SasSubType type) {
        return StaffSpecificJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .shift("L")
                .build();
    }
}
