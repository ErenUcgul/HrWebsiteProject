package com.hrproject.hrwebsiteproject.model.dto.request;

import java.time.LocalDate;

public record EmployeeLeaveRequestDto(

        Long companyLeaveTypeId,
        LocalDate startDate,
        LocalDate endDate,
        String reason
) {
}
