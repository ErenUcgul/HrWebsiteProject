package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeExpenseRepository extends JpaRepository<EmployeeExpense, Long> {

    List<EmployeeExpense> findAllByEmployeeId(Long employeeId);
    List<EmployeeExpense> findAllByEmployeeIdAndCompanyIdAndStatus(Long employeeId, Long companyId, EExpenseStatus status);
    List<EmployeeExpense> findAllByEmployeeIdAndStatus(Long employeeId, EExpenseStatus status);
    List<EmployeeExpense> findAllByCompanyId(Long companyId);


}
