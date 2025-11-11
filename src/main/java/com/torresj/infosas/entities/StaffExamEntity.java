package com.torresj.infosas.entities;

import com.torresj.infosas.enums.SasSubType;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private SasSubType type;

    @Column(updatable = false)
    private float total;

    @Column(updatable = false)
    private float op;

    @Column(updatable = false)
    private float con;

    @Column(updatable = false, nullable = false)
    private int position;

    @Column(updatable = false, nullable = false)
    private boolean provisional;

    @Column(updatable = false, nullable = false)
    private int examYear;
}
