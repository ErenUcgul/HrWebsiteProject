package com.hrproject.hrwebsiteproject.model.dto.response;

public record EmployeeDashboardDTO(   int leaveCount,
                                      int approvedLeaveCount,
                                      int expenseCount,
                                      int approvedExpenseCount,
                                      double totalExpensesThisMonth
                                     ) {
}
