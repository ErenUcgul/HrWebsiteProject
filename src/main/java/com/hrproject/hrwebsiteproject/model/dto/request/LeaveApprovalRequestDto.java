package com.hrproject.hrwebsiteproject.model.dto.request;

public record LeaveApprovalRequestDto(
        Long leaveRequestId,
        boolean approved,
        String rejectionReason
) {
}
