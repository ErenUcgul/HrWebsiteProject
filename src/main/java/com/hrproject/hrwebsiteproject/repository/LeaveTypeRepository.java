package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    boolean existsByNameIgnoreCase(String name);


    boolean existsByName(String name);
}
