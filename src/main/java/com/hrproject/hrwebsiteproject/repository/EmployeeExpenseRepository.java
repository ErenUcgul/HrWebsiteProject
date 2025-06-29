package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeExpenseRepository extends JpaRepository<EmployeeExpense, Long> {

    List<EmployeeExpense> findAllByEmployeeId(Long employeeId);
    List<EmployeeExpense> findAllByEmployeeIdAndCompanyIdAndStatus(Long employeeId, Long companyId, EExpenseStatus status);
    List<EmployeeExpense> findAllByEmployeeIdAndStatus(Long employeeId, EExpenseStatus status);
    List<EmployeeExpense> findAllByCompanyId(Long companyId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " +
            "FROM EmployeeExpense e " +
            "WHERE e.companyId = :companyId " +
            "AND e.expenseDate BETWEEN :start AND :end")
    double sumAmountByCompanyAndDateRange(
            @Param("companyId") Long companyId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    long countByCompanyIdAndStatus(Long companyId, EExpenseStatus status);

    long countByEmployeeId(Long employeeId);

    long countByEmployeeIdAndStatus(Long employeeId, EExpenseStatus status);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM EmployeeExpense e " +
            "WHERE e.employeeId = :employeeId AND e.expenseDate BETWEEN :start AND :end")
    double sumAmountByEmployeeAndDateRange(
            @Param("employeeId") Long employeeId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}

