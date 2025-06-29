package com.hrproject.hrwebsiteproject.model.dto.response;

public record CompanyDashboardDTO(
        long totalEmployees,
        long activeEmployees,
        long todaysNewEmployees,
        long todaysDepartures,

        long totalLeaves,
        long pendingLeaves,
        long approvedLeaves,
        long rejectedLeaves,

        double totalExpensesThisMonth,
        long pendingExpenses
) {
}
