package com.torresj.infosas.services;

import com.torresj.infosas.dtos.EnrichedSpecificStaffJobBankDto;
import com.torresj.infosas.dtos.EnrichedStaffDto;
import com.torresj.infosas.dtos.EnrichedStaffExamDto;
import com.torresj.infosas.dtos.EnrichedStaffJobBankDto;
import com.torresj.infosas.dtos.StaffDto;
import com.torresj.infosas.dtos.StaffExamDto;
import com.torresj.infosas.dtos.StaffJobBankDto;
import com.torresj.infosas.dtos.StaffSpecificJobBankDto;
import com.torresj.infosas.entities.staff.StaffEntity;
import com.torresj.infosas.entities.staff.StaffExamEntity;
import com.torresj.infosas.entities.staff.StaffJobBankEntity;
import com.torresj.infosas.entities.staff.StaffSpecificJobBankEntity;
import com.torresj.infosas.enums.SasSubType;
import com.torresj.infosas.enums.StaffType;
import com.torresj.infosas.exceptions.StaffNotFoundException;
import com.torresj.infosas.mappers.StaffMapper;
import com.torresj.infosas.repositories.staff.StaffExamRepository;
import com.torresj.infosas.repositories.staff.StaffJobBankRepository;
import com.torresj.infosas.repositories.staff.StaffRepository;
import com.torresj.infosas.repositories.staff.StaffSpecificJobBankRepository;
import com.torresj.infosas.services.impl.StaffServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Limit;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.torresj.infosas.enums.SasSubType.*;
import static com.torresj.infosas.enums.Status.ADMITIDA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private StaffExamRepository staffExamRepository;

    @Mock
    private StaffSpecificJobBankRepository staffSpecificJobBankRepository;

    @Mock
    private StaffJobBankRepository staffJobBankRepository;

    @Mock
    private StaffMapper staffMapper;

    @InjectMocks
    private StaffServiceImpl staffService;

    @Test
    void givenStaffs_whenGetAll_thenReturnListOfStaff() {
        StaffEntity staffEntity = getStaffEntity(StaffType.NURSE);
        StaffDto staffDto = getStaffDto(0,0,0);
        when(staffRepository.findAllBySurnameContainingIgnoreCase("name", Limit.of(100)))
                .thenReturn(Set.of(staffEntity));

        Set<StaffDto> result = staffService.getStaffsBySurname("name");

        assertThat(result).contains(staffDto);
    }

    @Test
    void givenStaffs_whenGetAllWithNameAndSurname_thenReturnListOfStaff() {
        when(staffRepository.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase("name", "surname", Limit.of(100)))
                .thenReturn(Set.of(
                        StaffEntity.builder()
                                .id(1L)
                                .name("name")
                                .surname("surname")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build(),
                        StaffEntity.builder()
                                .id(1L)
                                .name("name2")
                                .surname("surname2")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build(),
                        StaffEntity.builder()
                                .id(1L)
                                .name("name")
                                .surname("surname")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.FISIO))
                                .build()
                ));

        Set<StaffDto> result = staffService.getStaffs("name","surname", null);

        assertThat(result).hasSize(3);
    }

    @Test
    void givenStaffs_whenGetAllWithTypeAndSurname_thenReturnListOfStaff() {
        when(staffRepository.findAllBySurnameContainingIgnoreCaseAndTypesContaining("surname", StaffType.NURSE, Limit.of(100)))
                .thenReturn(Set.of(
                        StaffEntity.builder()
                                .id(1L)
                                .name("name")
                                .surname("surname")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build(),
                        StaffEntity.builder()
                                .id(1L)
                                .name("name2")
                                .surname("surname2")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build()
                ));

        Set<StaffDto> result = staffService.getStaffs(null,"surname", StaffType.NURSE);

        assertThat(result).hasSize(2);
    }

    @Test
    void givenStaffs_whenGetAllWithNameAndTypeAndSurname_thenReturnListOfStaff() {
        when(staffRepository.findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndTypesContaining("name","surname", StaffType.NURSE, Limit.of(100)))
                .thenReturn(Set.of(
                        StaffEntity.builder()
                                .id(1L)
                                .name("name")
                                .surname("surname")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build(),
                        StaffEntity.builder()
                                .id(1L)
                                .name("name2")
                                .surname("surname2")
                                .dni("xxxxxx")
                                .types(List.of(StaffType.NURSE))
                                .build()
                ));

        Set<StaffDto> result = staffService.getStaffs("name","surname", StaffType.NURSE);

        assertThat(result).hasSize(2);
    }

    @Test
    void givenStaffsWithJobBankAndExam_whenGetAll_thenReturnListOfStaff() {
        StaffEntity staffEntity = getStaffEntity(StaffType.NURSE);
        StaffDto staffDto = getStaffDto(1,1,1);
        when(staffRepository.findAllBySurnameContainingIgnoreCase("name", Limit.of(100)))
                .thenReturn(Set.of(staffEntity));
        when(staffExamRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(List.of(getStaffExamEntity(staffEntity.getId(), NURSE_EXAM, true)));
        when(staffSpecificJobBankRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(Set.of(getStaffSpecificJobBankEntity(staffEntity.getId(), NURSE_DIALYSIS_SPECIFIC_JOB_BANK)));
        when(staffJobBankRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(Set.of(getStaffJobBankEntity(staffEntity.getId(), TCAE_JOB_BANK)));

        Set<StaffDto> result = staffService.getStaffsBySurname("name");

        assertThat(result).contains(staffDto);
    }

    @Test
    void givenStaffWithJobBankAndExam_whenGetById_thenReturnStaff() {
        var staffEntity = getStaffEntity(StaffType.NURSE);
        var staffExamEntity = getStaffExamEntity(staffEntity.getId(), NURSE_EXAM, true);
        var staffSpecificJobBankEntity = getStaffSpecificJobBankEntity(staffEntity.getId(), NURSE_DIALYSIS_SPECIFIC_JOB_BANK);
        var staffJobBankEntity = getStaffJobBankEntity(staffEntity.getId(), TCAE_JOB_BANK);
        when(staffRepository.findById(staffEntity.getId())).thenReturn(Optional.of(staffEntity));
        when(staffExamRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(List.of(staffExamEntity));
        when(staffSpecificJobBankRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(Set.of(staffSpecificJobBankEntity));
        when(staffJobBankRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(Set.of(staffJobBankEntity));
        when(staffMapper.toStaffExamDto(staffExamEntity)).thenReturn(getStaffExamDto());
        when(staffMapper.toStaffSpecificJobBankDto(staffSpecificJobBankEntity)).thenReturn(getStaffSpecificJobBankDto());
        when(staffMapper.toStaffJobBankDto(staffJobBankEntity)).thenReturn(getStaffJobBankDto());

        EnrichedStaffDto result = staffService.getStaffById(staffEntity.getId());

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(staffEntity.getId());
        assertThat(result.name()).isEqualTo(staffEntity.getName());
        assertThat(result.staffSpecificJobBanks()).hasSize(1);
        assertThat(result.staffJobBanks()).hasSize(1);
        assertThat(result.staffExams()).hasSize(1);
    }

    @Test
    void givenStaffIdNotExist_whenGetById_thenAnErrorIsThrown() {
        Exception exception = assertThrows(StaffNotFoundException.class, () -> staffService.getStaffById(1L));

        assertThat(exception.getMessage())
                .isEqualTo("Could not find staff with id 1");
    }

    @Test
    void givenStaffWithExam_whenGetBySurnameAndType_thenStaffsAreReturned() {
        var staffEntity = getStaffEntity(StaffType.NURSE);
        var staffProvisionalExamEntity = getStaffExamEntity(staffEntity.getId(), NURSE_EXAM, true);
        var staffDefinitiveExamEntity = getStaffExamEntity(staffEntity.getId(), NURSE_EXAM, false);
        var enrichedStaffExample = getEnrichedStaffExamDto(
                staffEntity,staffProvisionalExamEntity,
                staffDefinitiveExamEntity
        );
        when(staffRepository.findAllBySurnameContainingIgnoreCase("name", Limit.of(100)))
                .thenReturn(Set.of(staffEntity));
        when(staffExamRepository.findByStaffId(staffEntity.getId()))
                .thenReturn(List.of(staffProvisionalExamEntity,staffDefinitiveExamEntity));
        when(staffMapper.toEnrichedStaffExamDto(staffEntity,List.of(staffProvisionalExamEntity,staffDefinitiveExamEntity)))
                .thenReturn(enrichedStaffExample);

        List<EnrichedStaffExamDto> result = staffService.getEnrichedStaffExam("name");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(enrichedStaffExample);
    }

    @Test
    void givenStaffWithJobBank_whenGetBySurnameAndType_thenReturnStaffsAreReturned() {
        var staffEntity = getStaffEntity(StaffType.NURSE);
        var staffJobBankEntity = getStaffJobBankEntity(staffEntity.getId(), TCAE_JOB_BANK);
        var enrichedStaffExample = getEnrichedStaffJobBankDto(
                staffEntity,
                staffJobBankEntity
        );
        when(staffRepository.findAllBySurnameContainingIgnoreCase("name", Limit.of(100)))
                .thenReturn(Set.of(staffEntity));
        when(staffJobBankRepository.findByStaffIdAndType(staffEntity.getId(),TCAE_JOB_BANK))
                .thenReturn(staffJobBankEntity);
        when(staffMapper.toEnrichedStaffJobBankDto(staffEntity,staffJobBankEntity))
                .thenReturn(enrichedStaffExample);

        Set<EnrichedStaffJobBankDto> result = staffService.getEnrichedStaffJobBank("name", TCAE_JOB_BANK);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().orElse(null)).isEqualTo(enrichedStaffExample);
    }

    @Test
    void givenStaffWithSpecificJobBank_whenGetBySurnameAndType_thenReturnStaffsAreReturned() {
        var staffEntity = getStaffEntity(StaffType.NURSE);
        var staffSpecificJobBankEntity = getStaffSpecificJobBankEntity(staffEntity.getId(), NURSE_DIALYSIS_SPECIFIC_JOB_BANK);
        var enrichedStaffExample = getEnrichedSpecificStaffJobBankDto(
                staffEntity,
                staffSpecificJobBankEntity
        );
        when(staffRepository.findAllBySurnameContainingIgnoreCase("name", Limit.of(100)))
                .thenReturn(Set.of(staffEntity));
        when(staffSpecificJobBankRepository.findByStaffIdAndType(staffEntity.getId(),NURSE_DIALYSIS_SPECIFIC_JOB_BANK))
                .thenReturn(staffSpecificJobBankEntity);
        when(staffMapper.toEnrichedSpecificStaffJobBankDto(staffEntity,staffSpecificJobBankEntity))
                .thenReturn(enrichedStaffExample);

        Set<EnrichedSpecificStaffJobBankDto> result = staffService.getEnrichedSpecificStaffJobBank(
                "name",
                NURSE_DIALYSIS_SPECIFIC_JOB_BANK
        );

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().orElse(null)).isEqualTo(enrichedStaffExample);
    }

    private StaffEntity getStaffEntity(StaffType staffType) {
        return
            StaffEntity.builder()
                .id(1L)
                .dni("dni")
                .name("name1")
                .surname("surname1")
                .types(List.of(staffType))
                .build();
    }

    private StaffExamEntity getStaffExamEntity(Long staffId, SasSubType type, boolean provisional) {
        return StaffExamEntity
                .builder()
                .staffId(staffId)
                .provisional(provisional)
                .type(type)
                .build();
    }

    private StaffSpecificJobBankEntity getStaffSpecificJobBankEntity(Long staffId, SasSubType type) {
        return StaffSpecificJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .build();
    }

    private StaffJobBankEntity getStaffJobBankEntity(Long staffId, SasSubType type) {
        return StaffJobBankEntity
                .builder()
                .staffId(staffId)
                .type(type)
                .build();
    }

    private StaffDto getStaffDto(int exams, int jobBanks, int specialJobBanks) {
        return new StaffDto(1L, "name1","surname1","dni", List.of(StaffType.NURSE), exams, jobBanks, specialJobBanks);
    }

    private StaffExamDto getStaffExamDto() {
        return new StaffExamDto(NURSE_EXAM,"L",false,0,0,0,0,2023);
    }

    private StaffSpecificJobBankDto getStaffSpecificJobBankDto() {
        return new StaffSpecificJobBankDto(
                "SI",
                "L",
                NURSE_CRITICS_SPECIFIC_JOB_BANK,
                ADMITIDA,
                ADMITIDA,
                List.of(),
                "",
                "",
                "",
                "",
                true,
                2023
        );
    }

    private StaffJobBankDto getStaffJobBankDto() {
        return new StaffJobBankDto(
                "SI",
                "L",
                ADMITIDA,
                TCAE_JOB_BANK,
                null,
                "",
                "",
                "",
                "",
                true,
                2023
        );
    }

    private EnrichedStaffExamDto getEnrichedStaffExamDto(
            StaffEntity staffEntity,
            StaffExamEntity provisional,
            StaffExamEntity definitive
    ) {
        return new EnrichedStaffExamDto(
            staffEntity.getId(),
            staffEntity.getName(),
            staffEntity.getSurname(),
            staffEntity.getDni(),
            List.of(
                    new StaffExamDto(
                            provisional.getType(),
                            provisional.getShift(),
                            provisional.isProvisional(),
                            provisional.getTotal(),
                            provisional.getOp(),
                            provisional.getCon(),
                            provisional.getPosition(),
                            provisional.getExamYear()
                    ),
                    new StaffExamDto(
                            definitive.getType(),
                            definitive.getShift(),
                            definitive.isProvisional(),
                            definitive.getTotal(),
                            definitive.getOp(),
                            definitive.getCon(),
                            definitive.getPosition(),
                            provisional.getExamYear()
                    )
            )
        );
    }

    private EnrichedStaffJobBankDto getEnrichedStaffJobBankDto(
            StaffEntity staffEntity,
            StaffJobBankEntity staffJobBankEntity
    ) {
        return new EnrichedStaffJobBankDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                new StaffJobBankDto(
                        staffJobBankEntity.getTreaty(),
                        staffJobBankEntity.getShift(),
                        staffJobBankEntity.getStatus(),
                        staffJobBankEntity.getType(),
                        null,
                        staffJobBankEntity.getExperience(),
                        staffJobBankEntity.getFormation(),
                        staffJobBankEntity.getOthers(),
                        staffJobBankEntity.getTotal(),
                        true,
                        2023
                )
        );
    }

    private EnrichedSpecificStaffJobBankDto getEnrichedSpecificStaffJobBankDto(
            StaffEntity staffEntity,
            StaffSpecificJobBankEntity staffJobBankEntity
    ) {
        return new EnrichedSpecificStaffJobBankDto(
                staffEntity.getId(),
                staffEntity.getName(),
                staffEntity.getSurname(),
                staffEntity.getDni(),
                new StaffSpecificJobBankDto(
                        staffJobBankEntity.getTreaty(),
                        staffJobBankEntity.getShift(),
                        staffJobBankEntity.getType(),
                        ADMITIDA,
                        ADMITIDA,
                        null,
                        staffJobBankEntity.getExperience(),
                        staffJobBankEntity.getFormation(),
                        staffJobBankEntity.getOthers(),
                        staffJobBankEntity.getTotal(),
                        true,
                        2023
                )
        );
    }
}
