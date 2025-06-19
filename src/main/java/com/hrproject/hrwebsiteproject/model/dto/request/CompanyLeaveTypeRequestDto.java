package com.hrproject.hrwebsiteproject.model.dto.request;

public record CompanyLeaveTypeRequestDto(
        Long leaveTypeId,
        Integer defaultDayCount,
        Boolean requiresApproval
) {
}
