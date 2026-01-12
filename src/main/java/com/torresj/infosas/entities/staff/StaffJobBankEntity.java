package com.torresj.infosas.entities.staff;

import com.torresj.infosas.enums.SasSubType;
import com.torresj.infosas.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(columnList = "staffId"),
        @Index(columnList = "staffId, type, cutOffYear", unique = true)
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StaffJobBankEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long staffId;

    @Column(updatable = false, nullable = false)
    private String shift;

    @Column(updatable = false)
    private String treaty;

    @Column(updatable = false)
    private Status status;

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private SasSubType type;

    @Column(updatable = false)
    private String exclusionCodes;

    @Column(updatable = false)
    private String experience;

    @Column(updatable = false)
    private String formation;

    @Column(updatable = false)
    private String others;

    @Column(updatable = false)
    private String total;

    @Column(updatable = false, nullable = false)
    private boolean provisional;

    @Column(updatable = false, nullable = false)
    private int cutOffYear;
}
