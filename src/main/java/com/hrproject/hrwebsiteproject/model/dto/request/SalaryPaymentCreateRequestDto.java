package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.YearMonth;

public record SalaryPaymentCreateRequestDto(
        Long employeeId,
        Long companyId,
        YearMonth paymentMonth
) {
}
