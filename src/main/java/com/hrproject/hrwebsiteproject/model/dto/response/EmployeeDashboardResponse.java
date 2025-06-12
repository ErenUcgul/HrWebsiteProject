package com.hrproject.hrwebsiteproject.model.dto.response;

import java.util.List;

public record EmployeeDashboardResponse(
         String fullName,
         int remainingLeaveDays,
         List<String> upcomingHolidays
) {
}
