package com.torresj.infosas.repositories.staff;

import com.torresj.infosas.entities.staff.StaffExamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffExamRepository extends CrudRepository<StaffExamEntity, Long> {
    List<StaffExamEntity> findByStaffId(Long id);
}
