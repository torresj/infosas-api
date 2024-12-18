package com.torresj.infosas.repositories;

import com.torresj.infosas.entities.StaffSpecificJobBankEntity;
import com.torresj.infosas.enums.SpecificJobBankType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StaffSpecificJobBankRepository extends CrudRepository<StaffSpecificJobBankEntity, Long> {
    Set<StaffSpecificJobBankEntity> findByStaffId(Long staffId);
    StaffSpecificJobBankEntity findByStaffIdAndType(Long staffId, SpecificJobBankType type);
}
