package com.hrproject.hrwebsiteproject.model.dto.response;

import lombok.Builder;

@Builder
public record AdminDashboardResponse(
         int totalCompanies,
         int totalUsers,
         int pendingCompanyApprovals
) {
}
