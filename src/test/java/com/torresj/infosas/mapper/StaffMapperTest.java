package com.torresj.infosas.mapper;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.StaffExamDto;
import com.torresj.infosas.dtos.StaffJobBankDto;
import com.torresj.infosas.dtos.StaffSpecificJobBankDto;
import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import com.torresj.infosas.mappers.StaffMapper;
import com.torresj.infosas.mappers.StaffMapperImpl;
import org.junit.jupiter.api.Test;

import static com.torresj.infosas.enums.JobBankType.TCAE;
import static com.torresj.infosas.enums.SpecificJobBankType.NURSE_DIALYSIS;
import static com.torresj.infosas.enums.StaffType.NURSE;
import static com.torresj.infosas.enums.Status.ADMITIDA;
import static org.assertj.core.api.Assertions.assertThat;

public class StaffMapperTest {
    private final StaffMapper staffMapper = new StaffMapperImpl();

    @Test
    void toExamDto(){
        StaffExamEntity staffExamEntity = StaffExamEntity.builder()
                .id(1L)
                .staffId(1L)
                .con(1)
                .position(2)
                .type(NURSE)
                .op(3)
                .provisional(true)
                .total(4)
                .build();

        StaffExamDto dto = staffMapper.toStaffExamDto(staffExamEntity);

        assertThat(dto).isNotNull();
        assertThat(dto.type()).isEqualTo(NURSE);
        assertThat(dto.provisional()).isEqualTo(true);
        assertThat(dto.con()).isEqualTo(1);
        assertThat(dto.op()).isEqualTo(3);
        assertThat(dto.total()).isEqualTo(4);
        assertThat(dto.position()).isEqualTo(2);
    }

    @Test
    void toStaffJobBankDto(){
        StaffJobBankEntity staffJobBankEntity = StaffJobBankEntity.builder()
                .id(1L)
                .staffId(1L)
                .exclusionCode("A01")
                .experience("1")
                .formation("2")
                .others("3")
                .status(ADMITIDA)
                .type(TCAE)
                .total("4")
                .treaty("SI")
                .shift("L")
                .build();

        StaffJobBankDto dto = staffMapper.toStaffJobBankDto(staffJobBankEntity);

        assertThat(dto).isNotNull();
        assertThat(dto.exclusionReason()).isNotNull();
        assertThat(dto.exclusionReason().code()).isEqualTo("A01");
        assertThat(dto.experience()).isEqualTo("1");
        assertThat(dto.formation()).isEqualTo("2");
        assertThat(dto.others()).isEqualTo("3");
        assertThat(dto.status()).isEqualTo(ADMITIDA);
        assertThat(dto.total()).isEqualTo("4");
        assertThat(dto.treaty()).isEqualTo("SI");
        assertThat(dto.shift()).isEqualTo("L");

    }

    @Test
    void toStaffSpecificJobBankDto(){
        StaffSpecificJobBankEntity staffSpecificJobBankEntity = StaffSpecificJobBankEntity.builder()
                .id(1L)
                .staffId(1L)
                .exclusionCodes("A01,A02")
                .experience("1")
                .formation("2")
                .others("3")
                .specific_admission(ADMITIDA)
                .general_admission(ADMITIDA)
                .type(NURSE_DIALYSIS)
                .total("4")
                .treaty("SI")
                .shift("L")
                .build();

        StaffSpecificJobBankDto dto = staffMapper.toStaffSpecificJobBankDto(staffSpecificJobBankEntity);

        assertThat(dto).isNotNull();
        assertThat(dto.exclusionReasons()).isNotNull();
        assertThat(dto.exclusionReasons()).hasSize(2);
        assertThat(dto.exclusionReasons().get(0).code()).isEqualTo("A01");
        assertThat(dto.exclusionReasons().get(1).code()).isEqualTo("A02");
        assertThat(dto.experience()).isEqualTo("1");
        assertThat(dto.formation()).isEqualTo("2");
        assertThat(dto.others()).isEqualTo("3");
        assertThat(dto.general_admission()).isEqualTo(ADMITIDA);
        assertThat(dto.specific_admission()).isEqualTo(ADMITIDA);
        assertThat(dto.type()).isEqualTo(NURSE_DIALYSIS);
        assertThat(dto.total()).isEqualTo("4");
        assertThat(dto.treaty()).isEqualTo("SI");
        assertThat(dto.shift()).isEqualTo("L");
    }

    @Test
    void toEnrichedStaffExamDto(){
        var staffEntity = StaffEntity.builder()
                .id(1L)
                .dni("dni")
                .name("name")
                .surname("surname")
                .build();

        var staffExamEntity1 = StaffExamEntity.builder().provisional(true).build();
        var staffExamEntity2 = StaffExamEntity.builder().provisional(false).build();

        EnrichedStaffExamDto dto = staffMapper.toEnrichedStaffExamDto(staffEntity,staffExamEntity1,staffExamEntity2);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(staffEntity.getId());
        assertThat(dto.dni()).isEqualTo("dni");
        assertThat(dto.name()).isEqualTo("name");
        assertThat(dto.surname()).isEqualTo("surname");
        assertThat(dto.provisionalExam()).isEqualTo(staffMapper.toStaffExamDto(staffExamEntity1));
        assertThat(dto.definitiveExam()).isEqualTo(staffMapper.toStaffExamDto(staffExamEntity2));
    }

    @Test
    void toEnrichedStaffExamDtoWithNulls(){
        var staffEntity = StaffEntity.builder()
                .id(1L)
                .dni("dni")
                .name("name")
                .surname("surname")
                .build();

        EnrichedStaffExamDto dto = staffMapper.toEnrichedStaffExamDto(staffEntity,null,null);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(staffEntity.getId());
        assertThat(dto.dni()).isEqualTo("dni");
        assertThat(dto.name()).isEqualTo("name");
        assertThat(dto.surname()).isEqualTo("surname");
        assertThat(dto.provisionalExam()).isNull();
        assertThat(dto.definitiveExam()).isNull();
    }

    @Test
    void toEnrichedStaffJobBankDto(){
        var staffEntity = StaffEntity.builder()
                .id(1L)
                .dni("dni")
                .name("name")
                .surname("surname")
                .build();

        var staffJobBankEntity = StaffJobBankEntity.builder().build();

        EnrichedStaffJobBankDto dto = staffMapper.toEnrichedStaffJobBankDto(staffEntity,staffJobBankEntity);

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(staffEntity.getId());
        assertThat(dto.dni()).isEqualTo("dni");
        assertThat(dto.name()).isEqualTo("name");
        assertThat(dto.surname()).isEqualTo("surname");
        assertThat(dto.staffJobBank()).isEqualTo(staffMapper.toStaffJobBankDto(staffJobBankEntity));
    }

    @Test
    void toEnrichedSpecificStaffJobBankDto(){
        var staffEntity = StaffEntity.builder()
                .id(1L)
                .dni("dni")
                .name("name")
                .surname("surname")
                .build();

        var staffJobBankEntity = StaffSpecificJobBankEntity.builder().build();

        EnrichedSpecificStaffJobBankDto dto = staffMapper.toEnrichedSpecificStaffJobBankDto(
                staffEntity,
                staffJobBankEntity
        );

        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(staffEntity.getId());
        assertThat(dto.dni()).isEqualTo("dni");
        assertThat(dto.name()).isEqualTo("name");
        assertThat(dto.surname()).isEqualTo("surname");
        assertThat(dto.staffJobBank()).isEqualTo(staffMapper.toStaffSpecificJobBankDto(staffJobBankEntity));
    }
}
