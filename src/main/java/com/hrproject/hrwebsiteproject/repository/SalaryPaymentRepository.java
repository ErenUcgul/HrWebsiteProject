package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.SalaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface SalaryPaymentRepository extends JpaRepository<SalaryPayment, Long> {

    List<SalaryPayment> findByCompanyId(Long companyId);
    List<SalaryPayment> findByEmployeeId(Long employeeId);
    Optional<SalaryPayment> findByEmployeeIdAndPaymentMonth(Long employeeId, YearMonth paymentMonth);

}
