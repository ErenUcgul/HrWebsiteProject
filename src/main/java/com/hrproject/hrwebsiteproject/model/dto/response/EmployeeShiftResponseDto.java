package com.hrproject.hrwebsiteproject.model.dto.response;

import java.time.LocalDate;

public record EmployeeShiftResponseDto(
        Long employeeId,
        Long userId,
        String firstName,
        String lastName,
        String email,
        LocalDate beginDate,
        LocalDate endDate
) {
}
