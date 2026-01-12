package com.torresj.infosas.repositories.staff;

import com.torresj.infosas.entities.staff.StaffJobBankEntity;
import com.torresj.infosas.enums.SasSubType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StaffJobBankRepository extends CrudRepository<StaffJobBankEntity, Long> {
    Set<StaffJobBankEntity> findByStaffId(Long staffId);
    StaffJobBankEntity findByStaffIdAndType(Long staffId, SasSubType type);
}
