package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.LocalDate;

public record EmployeeExpenseRequestDto(
        String description,
        Double amount,
        LocalDate expenseDate
) {
}