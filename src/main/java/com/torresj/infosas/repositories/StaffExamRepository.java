package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffExamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffExamRepository extends CrudRepository<StaffExamEntity, Long> {
    List<StaffExamEntity> findByStaffId(Long id);
}
