package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;

import java.time.LocalDate;

public record EmployeeExpenseResponseDto(
        Long id,
        String description,
        Double amount,
        LocalDate expenseDate,
        EExpenseStatus status,
        String rejectionReason
) {
}