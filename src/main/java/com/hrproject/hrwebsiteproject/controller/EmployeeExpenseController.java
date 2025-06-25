package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeExpenseRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeExpenseResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.service.EmployeeExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.EMPLOYEE_EXPENSE_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeExpenseController {
    private final EmployeeExpenseService expenseService;

    @PostMapping(EndPoints.CREATE_EXPENSE)
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> createExpense(
            @RequestParam Long employeeId,
            @RequestParam Long companyId,
            @RequestBody @Valid EmployeeExpenseRequestDto dto) {

        EmployeeExpenseResponseDto response = expenseService.createExpense(employeeId, companyId, dto);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message("Harcama talebi oluşturuldu.")
                .data(response)
                .build());
    }

    @GetMapping(EndPoints.MY_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getMyExpenses(@RequestParam Long employeeId) {
        List<EmployeeExpenseResponseDto> expenses = expenseService.getMyExpenses(employeeId);

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Harcamalar listelendi.")
                .data(expenses)
                .build());
    }

    @PutMapping(EndPoints.APPROVE_OR_REJECT_EXPENSE)
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> approveOrReject(
            @RequestParam Long expenseId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String reason) {

        EmployeeExpenseResponseDto response = expenseService.approveOrRejectExpense(expenseId, approved, reason);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message(approved ? "Harcama onaylandı." : "Harcama reddedildi.")
                .data(response)
                .build());
    }
    @PutMapping(EndPoints.UPDATE_EXPENSE)
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> updateExpense(
            @RequestParam Long expenseId,
            @RequestBody @Valid EmployeeExpenseRequestDto dto,
            @RequestParam Long employeeId
    ) {
        EmployeeExpenseResponseDto updated = expenseService.updateExpense(expenseId, dto, employeeId);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message("Harcama başarıyla güncellendi.")
                .data(updated)
                .build());
    }
    @DeleteMapping(EndPoints.DELETE_EXPENSE)
    public ResponseEntity<BaseResponse<Boolean>> deleteExpense(
            @RequestParam Long expenseId,
            @RequestParam Long employeeId
    ) {
        expenseService.deleteExpense(expenseId, employeeId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Harcama başarıyla silindi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.APPROVED_EXPENSES)
    public ResponseEntity<List<EmployeeExpense>> getApproved(@RequestParam Long employeeId) {
        return ResponseEntity.ok(expenseService.getApprovedExpenses(employeeId));
    }

    @GetMapping(EndPoints.REJECTED_EXPENSES)
    public ResponseEntity<List<EmployeeExpense>> getRejected(@RequestParam Long employeeId) {
        return ResponseEntity.ok(expenseService.getRejectedExpenses(employeeId));
    }

    @GetMapping(EndPoints.PENDING_EXPENSES)
    public ResponseEntity<List<EmployeeExpense>> getPending(@RequestParam Long employeeId) {
        return ResponseEntity.ok(expenseService.getPendingExpenses(employeeId));
    }
    @GetMapping(EndPoints.LIST_ALL_EXPENSES)
    public ResponseEntity<List<EmployeeExpense>> getAllExpensesByCompany(@RequestParam Long companyId) {
        return ResponseEntity.ok(expenseService.getAllExpensesByCompanyId(companyId));
    }
    /*
    token
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> createExpense(
            @RequestHeader String token,
            @RequestBody @Valid EmployeeExpenseRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long employeeId = employeeService.getEmployeeIdByUserId(userId);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        EmployeeExpenseResponseDto response = expenseService.createExpense(employeeId, companyId, dto);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message("Harcama talebi oluşturuldu.")
                .data(response)
                .build());
    }

    @GetMapping("/my-expenses")
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getMyExpenses(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);
        Long employeeId = employeeService.getEmployeeIdByUserId(userId);

        List<EmployeeExpenseResponseDto> expenses = expenseService.getMyExpenses(employeeId);

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Harcamalarınız listelendi.")
                .data(expenses)
                .build());
    }

    @PutMapping("/approve-reject")
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> approveOrReject(
            @RequestParam Long expenseId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String reason) {

        EmployeeExpenseResponseDto response = expenseService.approveOrRejectExpense(expenseId, approved, reason);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message(approved ? "Harcama onaylandı." : "Harcama reddedildi.")
                .data(response)
                .build());
    }
     */
}
