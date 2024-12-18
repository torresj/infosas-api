package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffJobBankEntity;
import com.torresj.infosas.enums.JobBankType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StaffJobBankRepository extends CrudRepository<StaffJobBankEntity, Long> {
    Set<StaffJobBankEntity> findByStaffId(Long staffId);
    StaffJobBankEntity findByStaffIdAndType(Long staffId, JobBankType type);
}
