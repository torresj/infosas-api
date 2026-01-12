package com.torresj.infosas.entities.staff;

import com.torresj.infosas.enums.StaffType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(indexes = {
        @Index(columnList = "surname, name, dni"),
        @Index(columnList = "name, surname"),
        @Index(columnList = "surname"),
        @Index(columnList = "dni")
})
@Data
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

    @Column
    @ElementCollection(targetClass = StaffType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<StaffType> types;
}
