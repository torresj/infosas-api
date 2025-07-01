package com.torresj.infosas.entities;

import com.torresj.infosas.enums.StaffExamType;
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
        @Index(columnList = "staffId, type, examYear", unique = true)
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StaffExamEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long staffId;

    @Column(updatable = false, nullable = false)
    private String shift;

    @Column(updatable = false, nullable = false)
    private StaffExamType type;

    @Column(updatable = false, nullable = false)
    private boolean provisional;

    @Column(updatable = false)
    private float total;

    @Column(updatable = false)
    private float op;

    @Column(updatable = false)
    private float con;

    @Column(updatable = false, nullable = false)
    private int position;

    @Column(updatable = false, nullable = false)
    private int examYear;
}
