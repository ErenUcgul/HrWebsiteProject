package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.response.SalaryPaymentResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.model.entity.SalaryPayment;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import com.hrproject.hrwebsiteproject.model.enums.ESalaryStatus;
import com.hrproject.hrwebsiteproject.repository.EmployeeExpenseRepository;
import com.hrproject.hrwebsiteproject.repository.SalaryPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryPaymentService {
    private final SalaryPaymentRepository salaryPaymentRepository;
    private final EmployeeExpenseRepository employeeExpenseRepository;
    private final EmployeeService employeeService;

    public SalaryPaymentResponseDto createMonthlySalaryPayment(Long employeeId, Long companyId, YearMonth month) {
        Employee employee = employeeService.findById(employeeId);

        Double baseSalary = employee.getSalary();

        Double approvedExpenses = employeeExpenseRepository
                .findAllByEmployeeIdAndCompanyIdAndStatus(employeeId, companyId, EExpenseStatus.APPROVED)

                .stream()
                .filter(e -> YearMonth.from(
                        Instant.ofEpochMilli(e.getCreateAt())   // Long → Instant
                                .atZone(ZoneId.systemDefault()) // → ZonedDateTime
                                .toLocalDate()                  // → LocalDate
                ).equals(month))                                // Month karşılaştırması
                .mapToDouble(EmployeeExpense::getAmount)
                .sum();

        Double totalPayable = baseSalary + approvedExpenses;

        SalaryPayment salaryPayment = SalaryPayment.builder()
                .employeeId(employeeId)
                .companyId(companyId)
                .paymentMonth(month)
                .baseSalary(baseSalary)
                .approvedExpenseTotal(approvedExpenses)
                .totalPayable(totalPayable)
                .status(ESalaryStatus.PENDING)
                .build();

        salaryPaymentRepository.save(salaryPayment);

        return toDto(salaryPayment);
    }

    public List<SalaryPaymentResponseDto> getPaymentsByEmployeeId(Long employeeId) {
        return salaryPaymentRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<SalaryPaymentResponseDto> getPaymentsByCompanyId(Long companyId) {
        return salaryPaymentRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void updateStatus(Long id, ESalaryStatus status) {
        SalaryPayment payment = salaryPaymentRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SALARY_PAYMENT_NOT_FOUND));
        payment.setStatus(status);
        salaryPaymentRepository.save(payment);
    }

    private SalaryPaymentResponseDto toDto(SalaryPayment sp) {
        return new SalaryPaymentResponseDto(
                sp.getId(),
                sp.getEmployeeId(),
                sp.getCompanyId(),
                sp.getPaymentMonth(),
                sp.getBaseSalary(),
                sp.getApprovedExpenseTotal(),
                sp.getTotalPayable(),
                sp.getStatus()
        );
    }

}