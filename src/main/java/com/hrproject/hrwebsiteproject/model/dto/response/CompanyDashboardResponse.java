package com.hrproject.hrwebsiteproject.model.dto.response;

import lombok.Builder;

@Builder
public record CompanyDashboardResponse(
         String companyName,
         int totalEmployees,
         int pendingLeaveRequests
) {
}
