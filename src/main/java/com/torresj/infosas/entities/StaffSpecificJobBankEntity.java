package com.torresj.infosas.entities;

import com.torresj.infosas.enums.SpecificJobBankType;
import com.torresj.infosas.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
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
public class StaffSpecificJobBankEntity {
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

    @Column(updatable = false, nullable = false)
    private SpecificJobBankType type;

    @Column(updatable = false)
    private Status general_admission;

    @Column(updatable = false)
    private Status specific_admission;

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
