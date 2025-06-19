package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.CompanyLeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyLeaveTypeRepository extends JpaRepository<CompanyLeaveType, Long> {
    List<CompanyLeaveType> findAllByCompanyId(Long companyId);

    Optional<CompanyLeaveType> findByCompanyIdAndLeaveTypeId(Long companyId, Long leaveTypeId);

    boolean existsByCompanyIdAndLeaveTypeId(Long companyId, Long leaveTypeId);
}
