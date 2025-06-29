package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.model.dto.response.CompanyDashboardDTO;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeDashboardDTO;
import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus;
import com.hrproject.hrwebsiteproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeLeaveRepository leaveRepository;
    private final EmployeeExpenseRepository expenseRepository;
    private final CompanyReviewRepository reviewRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftTrackingRepository shiftTrackingRepository;

    public CompanyDashboardDTO getCompanyDashboard(Long companyId) {
        long totalEmployees = employeeRepository.countByCompanyId(companyId);

        // Doğru enum kullanımıyla aktif personel sayısı
        long activeEmployees = employeeRepository
                .countByCompanyIdAndEmploymentStatus(companyId, EEmploymentStatus.ACTIVE);

        LocalDate today = LocalDate.now();

        // Doğru alan adı: dateOfEmployment
        long todaysNewEmployees = employeeRepository
                .countByCompanyIdAndDateOfEmployment(companyId, today);

        // Doğru alan adı: dateOfTermination
        long todaysDepartures = employeeRepository
                .countByCompanyIdAndDateOfTermination(companyId, today);

        long totalLeaves = leaveRepository.countByCompanyId(companyId);
        long pendingLeaves = leaveRepository.countByCompanyIdAndLeaveStatus(companyId, ELeaveStatus.PENDING);
        long approvedLeaves = leaveRepository.countByCompanyIdAndLeaveStatus(companyId, ELeaveStatus.APPROVED);
        long rejectedLeaves = leaveRepository.countByCompanyIdAndLeaveStatus(companyId, ELeaveStatus.REJECTED);

        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        double totalExpensesThisMonth = expenseRepository
                .sumAmountByCompanyAndDateRange(companyId, startOfMonth, endOfMonth);

        long pendingExpenses = expenseRepository
                .countByCompanyIdAndStatus(companyId, EExpenseStatus.PENDING);

        return new CompanyDashboardDTO(
                totalEmployees,
                activeEmployees,
                todaysNewEmployees,
                todaysDepartures,
                totalLeaves,
                pendingLeaves,
                approvedLeaves,
                rejectedLeaves,
                totalExpensesThisMonth,
                pendingExpenses
        );
    }
    public EmployeeDashboardDTO getEmployeeDashboard(Long employeeId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        int leaveCount = (int) leaveRepository.countByEmployeeId(employeeId);
        int approvedLeaveCount = (int) leaveRepository.countByEmployeeIdAndLeaveStatus(employeeId, ELeaveStatus.APPROVED);

        int expenseCount = (int) expenseRepository.countByEmployeeId(employeeId);
        int approvedExpenseCount = (int) expenseRepository.countByEmployeeIdAndStatus(employeeId, EExpenseStatus.APPROVED);

        double totalExpensesThisMonth = expenseRepository.sumAmountByEmployeeAndDateRange(employeeId, firstDayOfMonth, today);


        return new EmployeeDashboardDTO(
                leaveCount,
                approvedLeaveCount,
                expenseCount,
                approvedExpenseCount,
                totalExpensesThisMonth

        );
    }
}
