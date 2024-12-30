package com.torresj.infosas.controllers;

import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import com.torresj.infosas.enums.JobBankType;
import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.StaffExamType;
import com.torresj.infosas.enums.Status;
import com.torresj.infosas.repositories.StaffExamRepository;
import com.torresj.infosas.repositories.StaffJobBankRepository;
import com.torresj.infosas.repositories.StaffRepository;
import com.torresj.infosas.repositories.StaffSpecificJobBankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    void givenStaffsWhenGetStaffBySurnameThenReturnStaff(){
        //Given
        var staffEntity = staffRepository.save(
                StaffEntity.builder()
                        .name("Jaime")
                        .surname("Torres Benavente")
                        .dni("xxxxxxxxx")
                        .build()
        );

        staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(StaffExamType.NURSE)
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
                        .type(SpecificJobBankType.NURSE_CRITICS)
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
                        .type(JobBankType.NURSE)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCode("E21")
                        .experience("1")
                        .formation("2")
                        .others("3")
                        .treaty("SI")
                        .total("4")
                        .build()
        );

        String url = getBaseUri() + "?filter=torres";

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
                        .build()
        );

        var staffExamEntity =  staffExamRepository.save(
                StaffExamEntity.builder()
                        .staffId(staffEntity.getId())
                        .provisional(true)
                        .type(StaffExamType.NURSE)
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
                        .type(SpecificJobBankType.NURSE_CRITICS)
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
                        .type(JobBankType.NURSE)
                        .status(Status.EXCLUIDA)
                        .shift("L")
                        .exclusionCode("E21")
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
        assertThat(result.staffJobBanks().stream().findFirst().get().exclusionReason()).isNotNull();
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
}
