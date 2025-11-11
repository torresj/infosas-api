package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.ExclusionReasons;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.enums.Status;
import com.torresj.infosas.repositories.StaffExamRepository;
import com.torresj.infosas.repositories.StaffJobBankRepository;
import com.torresj.infosas.repositories.StaffRepository;
import com.torresj.infosas.repositories.StaffSpecificJobBankRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static com.torresj.infosas.enums.SasSubType.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StaffControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StaffExamRepository staffExamRepository;

    @Autowired
    private StaffJobBankRepository staffJobBankRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffSpecificJobBankRepository staffSpecificJobBankRepository;

    private String getBaseUri() {
        return "http://localhost:" + port + "/v1/staff";
    }

    @Test
    void givenStaffsWhenGetStaffByNameAndSurnameAndTypeThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Jaime")
                        .surname("Torres Benavente")
                        .dni("xxxxxxxxx")
                        .type(StaffType.NURSE)
                        .build()
        );

        staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(NURSE_EXAM)
                        .op(1)
                        .shift("L")
                        .total(3)
                        .con(2)
                        .position(100)
                        .build()
        );

        staffSpecificJobBankRepository.save(
                StaffSpecificJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_CRITICS_SPECIFIC_JOB_BANK)
                        .general_admission(Status.EXCLUIDA)
                        .specific_admission(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        staffJobBankRepository.save(
                StaffJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_EXAM)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "?name=jaime&surname=torres&type=NURSE";

        var result = restTemplate.getForObject(url, StaffDto[].class);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result[0].name()).isEqualTo(staffEntity.getName());
        assertThat(result[0].surname()).isEqualTo(staffEntity.getSurname());
        assertThat(result[0].dni()).isEqualTo(staffEntity.getDni());
        assertThat(result[0].exams()).isEqualTo(1);
        assertThat(result[0].specificJobBanks()).isEqualTo(1);
        assertThat(result[0].jobBanks()).isEqualTo(1);
    }

    @Test
    void givenARequestWithDniAndSurnameThenAnErrorIsReturned(){
        String url = getBaseUri() + "?dni=12345678A&surname=torres";

        var result = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void givenARequestWithNoDniAndNoSurnameThenAnErrorIsReturned(){
        String url = getBaseUri();

        var result = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,result.getStatusCode());
    }

    @Test
    void givenStaffWhenGetStaffByDniThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Test")
                        .surname("TestByDni")
                        .dni("***4567**")
                        .type(StaffType.NURSE)
                        .build()
        );

        staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(NURSE_EXAM)
                        .op(1)
                        .shift("L")
                        .total(3)
                        .con(2)
                        .position(100)
                        .build()
        );

        staffSpecificJobBankRepository.save(
                StaffSpecificJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_CRITICS_SPECIFIC_JOB_BANK)
                        .general_admission(Status.EXCLUIDA)
                        .specific_admission(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        staffJobBankRepository.save(
                StaffJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_EXAM)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "?dni=12345678A";

        var result = restTemplate.getForObject(url, StaffDto[].class);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result[0].name()).isEqualTo(staffEntity.getName());
        assertThat(result[0].surname()).isEqualTo(staffEntity.getSurname());
        assertThat(result[0].dni()).isEqualTo(staffEntity.getDni());
        assertThat(result[0].exams()).isEqualTo(1);
        assertThat(result[0].specificJobBanks()).isEqualTo(1);
        assertThat(result[0].jobBanks()).isEqualTo(1);
    }

    @Test
    void givenStaffWhenGetStaffByIdThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Test")
                        .surname("TestById")
                        .dni("xxxxxxxxx")
                        .type(StaffType.NURSE)
                        .build()
        );

        var staffExamEntity =  staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(NURSE_EXAM)
                        .op(1)
                        .shift("L")
                        .total(3)
                        .con(2)
                        .position(100)
                        .build()
        );

        var staffSpecificJobBankEntity = staffSpecificJobBankRepository.save(
                StaffSpecificJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_CRITICS_SPECIFIC_JOB_BANK)
                        .general_admission(Status.EXCLUIDA)
                        .specific_admission(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        var staffJobBankEntity = staffJobBankRepository.save(
                StaffJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_JOB_BANK)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "/" + staffEntity.getId();

        var result = restTemplate.getForObject(url, EnrichedStaffDto.class);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(staffEntity.getName());
        assertThat(result.surname()).isEqualTo(staffEntity.getSurname());
        assertThat(result.dni()).isEqualTo(staffEntity.getDni());
        assertThat(result.staffExams()).hasSize(1);
        assertThat(result.staffExams().getFirst().type()).isEqualTo(staffExamEntity.getType());
        assertThat(result.staffExams().getFirst().provisional()).isEqualTo(staffExamEntity.isProvisional());
        assertThat(result.staffExams().getFirst().shift()).isEqualTo(staffExamEntity.getShift());
        assertThat(result.staffExams().getFirst().total()).isEqualTo(staffExamEntity.getTotal());
        assertThat(result.staffExams().getFirst().con()).isEqualTo(staffExamEntity.getCon());
        assertThat(result.staffExams().getFirst().position()).isEqualTo(staffExamEntity.getPosition());
        assertThat(result.staffJobBanks()).hasSize(1);
        assertThat(result.staffJobBanks().stream().findFirst().get().type()).isEqualTo(staffJobBankEntity.getType());
        assertThat(result.staffJobBanks().stream().findFirst().get().treaty()).isEqualTo(staffJobBankEntity.getTreaty());
        assertThat(result.staffJobBanks().stream().findFirst().get().shift()).isEqualTo(staffJobBankEntity.getShift());
        assertThat(result.staffJobBanks().stream().findFirst().get().status()).isEqualTo(staffJobBankEntity.getStatus());
        assertThat(result.staffJobBanks().stream().findFirst().get().experience()).isEqualTo(staffJobBankEntity.getExperience());
        assertThat(result.staffJobBanks().stream().findFirst().get().formation()).isEqualTo(staffJobBankEntity.getFormation());
        assertThat(result.staffJobBanks().stream().findFirst().get().total()).isEqualTo(staffJobBankEntity.getTotal());
        assertThat(result.staffJobBanks().stream().findFirst().get().others()).isEqualTo(staffJobBankEntity.getOthers());
        assertThat(result.staffJobBanks().stream().findFirst().get().exclusionReasons()).isNotNull();
        assertThat(result.staffSpecificJobBanks()).hasSize(1);
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().type()).isEqualTo(staffSpecificJobBankEntity.getType());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().treaty()).isEqualTo(staffSpecificJobBankEntity.getTreaty());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().shift()).isEqualTo(staffSpecificJobBankEntity.getShift());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().specific_admission()).isEqualTo(staffSpecificJobBankEntity.getSpecific_admission());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().general_admission()).isEqualTo(staffSpecificJobBankEntity.getGeneral_admission());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().experience()).isEqualTo(staffSpecificJobBankEntity.getExperience());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().formation()).isEqualTo(staffSpecificJobBankEntity.getFormation());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().others()).isEqualTo(staffSpecificJobBankEntity.getOthers());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().total()).isEqualTo(staffSpecificJobBankEntity.getTotal());
        assertThat(result.staffSpecificJobBanks().stream().findFirst().get().exclusionReasons()).hasSize(1);
    }

    @Test
    void givenStaffWithExamsWhenGetStaffBySurnameAndTypeThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Test")
                        .surname("TestForExams")
                        .dni("xxxxxxxxx")
                        .type(StaffType.NURSE)
                        .build()
        );

        var staffExamEntity =  staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(NURSE_EXAM)
                        .op(1)
                        .shift("L")
                        .total(3)
                        .con(2)
                        .position(100)
                        .build()
        );

        String url = getBaseUri() + "/exams?filter=TestForExams&type=NURSE";

        var result = restTemplate.getForObject(url, EnrichedStaffExamDto[].class);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result[0].name()).isEqualTo(staffEntity.getName());
        assertThat(result[0].surname()).isEqualTo(staffEntity.getSurname());
        assertThat(result[0].dni()).isEqualTo(staffEntity.getDni());
        assertThat(result[0].exams()).isNotNull();
        assertThat(result[0].exams()).isNotEmpty();
        assertThat(result[0].exams().getFirst().provisional()).isEqualTo(staffExamEntity.isProvisional());
        assertThat(result[0].exams().getFirst().type()).isEqualTo(staffExamEntity.getType());
        assertThat(result[0].exams().getFirst().shift()).isEqualTo(staffExamEntity.getShift());
        assertThat(result[0].exams().getFirst().total()).isEqualTo(staffExamEntity.getTotal());
        assertThat(result[0].exams().getFirst().con()).isEqualTo(staffExamEntity.getCon());
        assertThat(result[0].exams().getFirst().op()).isEqualTo(staffExamEntity.getOp());
        assertThat(result[0].exams().getFirst().position()).isEqualTo(staffExamEntity.getPosition());
    }

    @Test
    void givenStaffWithJobBanksWhenGetStaffBySurnameAndTypeThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Test")
                        .surname("TestForJobBanks")
                        .dni("xxxxxxxxx")
                        .type(StaffType.NURSE)
                        .build()
        );

        var staffJobBankEntity = staffJobBankRepository.save(
                StaffJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_JOB_BANK)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "/jobbanks?filter=TestForJobBanks&type=NURSE_JOB_BANK";

        var result = restTemplate.getForObject(url, EnrichedStaffJobBankDto[].class);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(Arrays.stream(result).findFirst().get().dni()).isEqualTo(staffEntity.getDni());
        assertThat(Arrays.stream(result).findFirst().get().name()).isEqualTo(staffEntity.getName());
        assertThat(Arrays.stream(result).findFirst().get().surname()).isEqualTo(staffEntity.getSurname());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank()).isNotNull();
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().status()).isEqualTo(staffJobBankEntity.getStatus());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().type()).isEqualTo(staffJobBankEntity.getType());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().shift()).isEqualTo(staffJobBankEntity.getShift());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().treaty()).isEqualTo(staffJobBankEntity.getTreaty());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().formation()).isEqualTo(staffJobBankEntity.getFormation());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().experience()).isEqualTo(staffJobBankEntity.getExperience());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().others()).isEqualTo(staffJobBankEntity.getOthers());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().total()).isEqualTo(staffJobBankEntity.getTotal());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().exclusionReasons()).isEqualTo(List.of(ExclusionReasons.getExclusionReason(staffJobBankEntity.getExclusionCodes())));
    }

    @Test
    void givenStaffWithSpecificJobBanksWhenGetStaffBySurnameAndTypeThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Test")
                        .surname("TestForSpecificJobBanks")
                        .dni("xxxxxxxxx")
                        .type(StaffType.NURSE)
                        .build()
        );

        var staffJobBankEntity = staffSpecificJobBankRepository.save(
                StaffSpecificJobBankEntity.builder()
                        .staffId(staffEntity.getId())
                        .type(NURSE_CRITICS_SPECIFIC_JOB_BANK)
                        .general_admission(Status.EXCLUIDA)
                        .specific_admission(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCodes("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "/specificjobbanks?filter=TestForSpecificJobBanks&type=NURSE_CRITICS_SPECIFIC_JOB_BANK";

        var result = restTemplate.getForObject(url, EnrichedSpecificStaffJobBankDto[].class);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(Arrays.stream(result).findFirst().get().dni()).isEqualTo(staffEntity.getDni());
        assertThat(Arrays.stream(result).findFirst().get().name()).isEqualTo(staffEntity.getName());
        assertThat(Arrays.stream(result).findFirst().get().surname()).isEqualTo(staffEntity.getSurname());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank()).isNotNull();
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().specific_admission()).isEqualTo(staffJobBankEntity.getSpecific_admission());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().general_admission()).isEqualTo(staffJobBankEntity.getGeneral_admission());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().type()).isEqualTo(staffJobBankEntity.getType());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().shift()).isEqualTo(staffJobBankEntity.getShift());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().treaty()).isEqualTo(staffJobBankEntity.getTreaty());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().formation()).isEqualTo(staffJobBankEntity.getFormation());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().experience()).isEqualTo(staffJobBankEntity.getExperience());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().others()).isEqualTo(staffJobBankEntity.getOthers());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().total()).isEqualTo(staffJobBankEntity.getTotal());
        assertThat(Arrays.stream(result).findFirst().get().staffJobBank().exclusionReasons()).isEqualTo(List.of(ExclusionReasons.getExclusionReason(staffJobBankEntity.getExclusionCodes())));
    }
}
