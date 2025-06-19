package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus;

import java.time.LocalDate;

public record EmployeeLeaveResponseDto(
        Long id,
        Long employeeId,
        Long companyLeaveTypeId,
        LocalDate startDate,
        LocalDate endDate,
        int totalDays,
        ELeaveStatus leaveStatus,
        String reason,
        Long leaveTypeId,
        String leaveTypeName,
        String leaveTypeDescription
) {
}
