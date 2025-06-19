package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeLeave;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeLeaveRepository extends JpaRepository<EmployeeLeave, Long> {


}
