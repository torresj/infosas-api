package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffEntity;
import com.torresj.infosas.enums.StaffType;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, Long> {
    Set<StaffEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
    Set<StaffEntity> findAllByDni(String dni, Limit limit);
    Set<StaffEntity> findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname, Limit limit);
    Set<StaffEntity> findAllBySurnameContainingIgnoreCaseAndTypesContaining(String name, StaffType type, Limit limit);
    Set<StaffEntity> findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseAndTypesContaining(String name, String surname, StaffType type, Limit limit);
}
