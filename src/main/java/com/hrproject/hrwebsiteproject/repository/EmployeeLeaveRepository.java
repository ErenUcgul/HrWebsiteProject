package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.EmployeeLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long> {

    @Query("""
    SELECT COALESCE(SUM(e.totalDays), 0)
    FROM EmployeeLeave e
    WHERE e.employeeId = :employeeId
      AND e.companyLeaveTypeId = :companyLeaveTypeId
      AND e.leaveStatus IN (
          com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus.PENDING,
          com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus.APPROVED
      )
""")
    int getRequestedAndApprovedLeaveDays(Long employeeId, Long companyLeaveTypeId);

    List<EmployeeLeave> findAllByEmployeeId(Long employeeId);

}
