package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.ESalaryStatus;

import java.time.YearMonth;

public record SalaryPaymentResponseDto(
        Long id,
        Long employeeId,
        Long companyId,
        YearMonth paymentMonth,
        Double baseSalary,
        Double approvedExpenseTotal,
        Double totalPayable,
        ESalaryStatus status
) {
}
