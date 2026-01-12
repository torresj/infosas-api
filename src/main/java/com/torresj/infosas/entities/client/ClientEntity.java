package com.torresj.infosas.entities.client;

import com.torresj.infosas.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClientEntity {
    @Id
    @Column(updatable = false)
    private Long id;

    @Column(updatable = false, nullable = false)
    private String password;

    @Column(nullable = false, updatable = false, unique = true)
    private String name;

    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
