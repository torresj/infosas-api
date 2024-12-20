package com.torresj.infosas.entities;

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
        @Index(columnList = "dni, name, surname", unique = true),
        @Index(columnList = "surname")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StaffEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    private String dni;

    @Column(updatable = false, nullable = false)
    private String name;

    @Column(updatable = false, nullable = false)
    private String surname;
}
