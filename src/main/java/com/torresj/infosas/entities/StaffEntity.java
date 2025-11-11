package com.torresj.infosas.entities;

import com.torresj.infosas.enums.StaffType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(columnList = "dni, name, surname, type", unique = true),
        @Index(columnList = "name, surname"),
        @Index(columnList = "name, surname, type"),
        @Index(columnList = "dni")
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

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffType type;
}
