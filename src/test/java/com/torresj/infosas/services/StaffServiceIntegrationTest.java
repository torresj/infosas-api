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
import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.StaffExamType;
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

import static com.torresj.infosas.enums.JobBankType.TCAE;
import static com.torresj.infosas.enums.SpecificJobBankType.NURSE_CRITICS;
import static com.torresj.infosas.enums.SpecificJobBankType.NURSE_DIALYSIS;
import static com.torresj.infosas.enums.StaffExamType.NURSE;
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
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("name2")
                        .surname("test22")
                        .dni("dni2")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("name3")
                        .surname("test23")
                        .dni("dni3")
                        .type(StaffType.NURSE)
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        StaffDto staffDto1 = new StaffDto(
                entitesList.get(2).getId(),
                "name3",
                "test23",
                "dni3",
                StaffType.NURSE,
                0,
                0,
                0
        );

        StaffDto staffDto2 = new StaffDto(
                entitesList.get(1).getId(),
                "name2",
                "test22",
                "dni2",
                StaffType.NURSE,
                1,
                1,
                2
        );

        staffExamRepository.save(givenStaffExam(entitesList.get(1).getId(), NURSE, false));
        staffJobBankRepository.save(givenStaffJobBank(entitesList.get(1).getId(), TCAE));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(1).getId(),NURSE_CRITICS));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(1).getId(),NURSE_DIALYSIS));

        //when
        Set<StaffDto> staffs = staffService.getStaffsBySurname("test2");

        //then
        assertThat(staffs).hasSize(2);
        assertThat(staffs).contains(staffDto1);
        assertThat(staffs).contains(staffDto2);
    }

    @Test
    void givenStaffsWhenGetStaffByNameAndSurnameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name")
                        .surname("test-surname")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name2")
                        .surname("test-surname2")
                        .dni("dni2")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name3")
                        .surname("test-surname3")
                        .dni("dni3")
                        .type(StaffType.NURSE)
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        StaffDto staffDto1 = new StaffDto(
                entitesList.get(2).getId(),
                "test-name3",
                "test-surname3",
                "dni3",
                StaffType.NURSE,
                1,
                1,
                2
        );

        staffExamRepository.save(givenStaffExam(entitesList.get(2).getId(), NURSE, false));
        staffJobBankRepository.save(givenStaffJobBank(entitesList.get(2).getId(), JobBankType.NURSE));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(2).getId(),NURSE_CRITICS));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entitesList.get(2).getId(),NURSE_DIALYSIS));

        //when
        Set<StaffDto> staffs = staffService.getStaffs("name3", "surname3", null);

        //then
        assertThat(staffs).hasSize(1);
        assertThat(staffs).contains(staffDto1);
    }

    @Test
    void givenStaffsWhenGetStaffByTypeAndSurnameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name4")
                        .surname("test-surname4")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name5")
                        .surname("test-surname5")
                        .dni("dni2")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name6")
                        .surname("test-surname6")
                        .dni("dni3")
                        .type(StaffType.FISIO)
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        StaffDto staffDto1 = new StaffDto(
                entitesList.get(0).getId(),
                "test-name4",
                "test-surname4",
                "dni",
                StaffType.NURSE,
                0,
                0,
                0
        );

        StaffDto staffDto2 = new StaffDto(
                entitesList.get(1).getId(),
                "test-name5",
                "test-surname5",
                "dni2",
                StaffType.NURSE,
                0,
                0,
                0
        );

        //when
        Set<StaffDto> staffs = staffService.getStaffs(null, "test-surname", StaffType.NURSE);

        //then
        assertThat(staffs).hasSize(2);
        assertThat(staffs).contains(staffDto1);
        assertThat(staffs).contains(staffDto2);
    }

    @Test
    void givenStaffsWhenGetStaffByTypeAndSurnameAndNameThenReturnStaff() {
        //Given
        List<StaffEntity> entities = List.of(
                StaffEntity.builder()
                        .name("test-name7")
                        .surname("test-surname7")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name8")
                        .surname("test-surname8")
                        .dni("dni2")
                        .type(StaffType.NURSE)
                        .build(),
                StaffEntity.builder()
                        .name("test-name9")
                        .surname("test-surname9")
                        .dni("dni3")
                        .type(StaffType.OCCUPATIONAL_THERAPY)
                        .build()
        );

        var entitesList = Streamable.of(staffRepository.saveAll(entities)).toList();

        StaffDto staffDto1 = new StaffDto(
                entitesList.get(2).getId(),
                "test-name9",
                "test-surname9",
                "dni3",
                StaffType.OCCUPATIONAL_THERAPY,
                0,
                0,
                0
        );

        //when
        Set<StaffDto> staffs = staffService.getStaffs("test-name", "test-surname", StaffType.OCCUPATIONAL_THERAPY);

        //then
        assertThat(staffs).hasSize(1);
        assertThat(staffs).contains(staffDto1);
    }

    @Test
    void givenStaffWhenGetStaffByIdThenReturnStaff() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("surnameIt")
                        .name("name")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build()
        );

        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE, false));
        staffJobBankRepository.save(givenStaffJobBank(entity.getId(), TCAE));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(),NURSE_CRITICS));
        staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(),NURSE_DIALYSIS));

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
                        .type(StaffType.NURSE)
                        .build()
        );

        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE, true));
        staffExamRepository.save(givenStaffExam(entity.getId(), StaffExamType.TCAE, false));

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
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE);
        assertThat(staffs.getFirst().exams().get(1).provisional()).isFalse();
        assertThat(staffs.getFirst().exams().get(1).type()).isEqualTo(StaffExamType.TCAE);
    }

    @Test
    void givenStaffWithProvisionalExamsWhenGetStaffsBySurnameAndTypeThenReturnStaffWithExam() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithProvisionalExam")
                        .name("name")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build()
        );
        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE, true));

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
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE);
    }

    @Test
    void givenStaffWithDefinitiveExamsWhenGetStaffsBySurnameAndTypeThenReturnStaffWithExam() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithDefinitiveExam")
                        .name("name")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build()
        );
        staffExamRepository.save(givenStaffExam(entity.getId(), NURSE, false));

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
        assertThat(staffs.getFirst().exams().getFirst().type()).isEqualTo(NURSE);
    }

    @Test
    void givenStaffWithJobBankWhenGetStaffsBySurnameAndTypeThenReturnStaffWithJobBank() {
        //Given
        var entity = staffRepository.save(
                StaffEntity.builder()
                        .surname("staffWithJobBank")
                        .name("name")
                        .dni("dni")
                        .type(StaffType.NURSE)
                        .build()
        );
        var staffJobBankEntity = staffJobBankRepository.save(givenStaffJobBank(entity.getId(), TCAE));

        //when
        Set<EnrichedStaffJobBankDto> staffs = staffService.getEnrichedStaffJobBank("staffWithJobBank", TCAE);

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
                        .type(StaffType.NURSE)
                        .build()
        );
        var staffJobBankEntity = staffSpecificJobBankRepository.save(givenStaffSpecificJobBank(entity.getId(), NURSE_CRITICS));

        //when
        Set<EnrichedSpecificStaffJobBankDto> staffs = staffService.getEnrichedSpecificStaffJobBank(
                "staffWithSpecificJobBank",
                NURSE_CRITICS
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

    private StaffExamEntity givenStaffExam(Long staffId, StaffExamType type, boolean provisional) {
        return StaffExamEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .provisional(provisional)
                .shift("L")
                .build();
    }

    private StaffJobBankEntity givenStaffJobBank(Long staffId, JobBankType type) {
        return StaffJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .shift("L")
                .build();
    }

    private StaffSpecificJobBankEntity givenStaffSpecificJobBank(Long staffId, SpecificJobBankType type) {
        return StaffSpecificJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .shift("L")
                .build();
    }
}
