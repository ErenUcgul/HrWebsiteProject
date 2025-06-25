package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.EmployeeExpenseMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeExpenseRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeExpenseResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.model.enums.EExpenseStatus;
import com.hrproject.hrwebsiteproject.repository.EmployeeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeExpenseService {
    private final EmployeeExpenseRepository employeeExpenseRepository;
    private final EmployeeService employeeService;
    private final EmployeeExpenseMapper employeeExpenseMapper;

    public EmployeeExpenseResponseDto createExpense(Long employeeId, Long companyId, EmployeeExpenseRequestDto dto) {
        EmployeeExpense expense = employeeExpenseMapper.toEntity(dto);
        expense.setEmployeeId(employeeId);
        expense.setCompanyId(companyId);
        expense.setStatus(EExpenseStatus.PENDING);

        employeeExpenseRepository.save(expense);
        return employeeExpenseMapper.toDto(expense);
    }

    public List<EmployeeExpenseResponseDto> getMyExpenses(Long employeeId) {
        return employeeExpenseRepository.findAllByEmployeeId(employeeId).stream()
                .map(employeeExpenseMapper::toDto)
                .toList();
    }

    public EmployeeExpenseResponseDto approveOrRejectExpense(Long expenseId, boolean approved, String reason) {
        EmployeeExpense expense = employeeExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EXPENSE_NOT_FOUND));

        if (!expense.getStatus().equals(EExpenseStatus.PENDING)) {
            throw new HrWebsiteProjectException(ErrorType.EXPENSE_ALREADY_PROCESSED);
        }

        if (approved) {
            expense.setStatus(EExpenseStatus.APPROVED);
        } else {
            expense.setStatus(EExpenseStatus.REJECTED);
            expense.setRejectionReason(reason);
        }

        employeeExpenseRepository.save(expense);
        return employeeExpenseMapper.toDto(expense);
    }
    public EmployeeExpenseResponseDto updateExpense(Long expenseId, EmployeeExpenseRequestDto dto, Long employeeId) {
        EmployeeExpense expense = employeeExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EXPENSE_NOT_FOUND));

        // Kullanıcı kendi harcamasını güncelleyebilir
        if (!expense.getEmployeeId().equals(employeeId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        // Sadece PENDING durumdaki harcama güncellenebilir
        if (!expense.getStatus().equals(EExpenseStatus.PENDING)) {
            throw new HrWebsiteProjectException(ErrorType.EXPENSE_ALREADY_PROCESSED);
        }

        // Güncelleme
        expense.setAmount(dto.amount());
        expense.setDescription(dto.description());
        expense.setExpenseDate(dto.expenseDate());

        employeeExpenseRepository.save(expense);

        return employeeExpenseMapper.toDto(expense);
    }
    public void deleteExpense(Long expenseId, Long employeeId) {
        EmployeeExpense expense = employeeExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EXPENSE_NOT_FOUND));

        // Kullanıcı sadece kendi harcamasını silebilir
        if (!expense.getEmployeeId().equals(employeeId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        // Sadece PENDING durumdaki harcama silinebilir
        if (!expense.getStatus().equals(EExpenseStatus.PENDING)) {
            throw new HrWebsiteProjectException(ErrorType.EXPENSE_ALREADY_PROCESSED);
        }

        employeeExpenseRepository.delete(expense);
    }
    public List<EmployeeExpense> getExpensesByStatus(Long employeeId, EExpenseStatus status) {
        return employeeExpenseRepository.findAllByEmployeeIdAndStatus(employeeId, status);
    }

    public List<EmployeeExpense> getApprovedExpenses(Long employeeId) {
        return getExpensesByStatus(employeeId, EExpenseStatus.APPROVED);
    }

    public List<EmployeeExpense> getRejectedExpenses(Long employeeId) {
        return getExpensesByStatus(employeeId, EExpenseStatus.REJECTED);
    }

    public List<EmployeeExpense> getPendingExpenses(Long employeeId) {
        return getExpensesByStatus(employeeId, EExpenseStatus.PENDING);
    }
    public List<EmployeeExpense> getAllExpensesByCompanyId(Long companyId) {
        return employeeExpenseRepository.findAllByCompanyId(companyId);
    }

}
