package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffExamEntity;
import com.torresj.infosas.enums.StaffType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffExamRepository extends CrudRepository<StaffExamEntity, Long> {
    List<StaffExamEntity> findByStaffId(Long id);
    StaffExamEntity findByStaffIdAndTypeAndProvisional(Long staffId, StaffType type, boolean provisional);
}
