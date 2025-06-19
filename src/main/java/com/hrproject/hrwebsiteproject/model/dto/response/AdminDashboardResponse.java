package com.hrproject.hrwebsiteproject.model.dto.response;

import lombok.Builder;

@Builder
public record AdminDashboardResponse(
        int totalUsers,
        int activeUsers,
        int pendingUsers,
        int inReviewUsers,
        int rejectedUsers,

        int totalCompanies,
        int activeCompanies,
        int pendingCompanies,
        int inReviewCompanies,
        int rejectedCompanies,

        int usersRegisteredLast7Days,
        int companiesRegisteredLast7Days
) {
}
