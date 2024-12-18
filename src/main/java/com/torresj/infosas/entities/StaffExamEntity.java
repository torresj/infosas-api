package com.torresj.infosas.entities;

import com.torresj.infosas.enums.StaffType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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
    private StaffType type;

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

}
