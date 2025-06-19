package com.hrproject.hrwebsiteproject.model.dto.response;

public record CompanyLeaveTypeResponseDto(
        Long id,
        String leaveTypeName,
        String leaveTypeDescription,
        Integer defaultDayCount,
        Boolean requiresApproval,
        Boolean active
) {
}
