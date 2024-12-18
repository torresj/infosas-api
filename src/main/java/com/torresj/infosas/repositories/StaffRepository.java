package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StaffRepository extends CrudRepository<StaffEntity, Long> {
    Set<StaffEntity> findAllBySurnameContainingIgnoreCase(String surname, Limit limit);
}
