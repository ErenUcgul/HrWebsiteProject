package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.mapper.EmployeeExpenseMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeExpenseRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeExpenseResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import com.hrproject.hrwebsiteproject.service.EmployeeExpenseService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
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
    private final JwtManager jwtManager;
    private final EmployeeExpenseMapper employeeExpenseMapper;


    @PostMapping(EndPoints.CREATE_EXPENSE)
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> createExpense(
            @RequestHeader String token,
            @RequestBody @Valid EmployeeExpenseRequestDto dto) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        Long companyId = jwtManager.getCompanyIdFromToken(token);

        EmployeeExpenseResponseDto response = expenseService.createExpense(employeeId, companyId, dto);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message("Harcama talebi oluşturuldu.")
                .data(response)
                .build());
    }

    @GetMapping(EndPoints.MY_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getMyExpenses(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
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
            @RequestHeader String token,
            @RequestParam Long expenseId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String reason) {

        Long adminId = jwtManager.getUserIdFromToken(token);

        EmployeeExpenseResponseDto response = expenseService.approveOrRejectExpense(expenseId, approved, reason);

        return ResponseEntity.ok(BaseResponse.<EmployeeExpenseResponseDto>builder()
                .code(200)
                .success(true)
                .message(approved ? "Harcama onaylandı, maaşa eklendi." : "Harcama reddedildi.")
                .data(response)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_EXPENSE)
    public ResponseEntity<BaseResponse<EmployeeExpenseResponseDto>> updateExpense(
            @RequestHeader String token,
            @RequestParam Long expenseId,
            @RequestBody @Valid EmployeeExpenseRequestDto dto) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
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
            @RequestHeader String token,
            @RequestParam Long expenseId) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        expenseService.deleteExpense(expenseId, employeeId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Harcama başarıyla silindi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.APPROVED_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getApproved(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        List<EmployeeExpenseResponseDto> approvedExpenses = expenseService.getApprovedExpenses(employeeId).stream()
                .map(employeeExpenseMapper::toDto)
                .toList();

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Onaylanan harcamalar listelendi.")
                .data(approvedExpenses)
                .build());
    }

    @GetMapping(EndPoints.REJECTED_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getRejected(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        List<EmployeeExpenseResponseDto> rejectedExpenses = expenseService.getRejectedExpenses(employeeId)
                .stream()
                .map(employeeExpenseMapper::toDto)
                .toList();

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Reddedilen harcamalar listelendi.")
                .data(rejectedExpenses)
                .build());
    }

    @GetMapping(EndPoints.PENDING_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getPending(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        List<EmployeeExpenseResponseDto> pendingExpenses = expenseService.getPendingExpenses(employeeId)
                .stream()
                .map(employeeExpenseMapper::toDto)
                .toList();

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Beklemede olan harcamalar listelendi.")
                .data(pendingExpenses)
                .build());
    }

    @GetMapping(EndPoints.LIST_ALL_EXPENSES)
    public ResponseEntity<BaseResponse<List<EmployeeExpenseResponseDto>>> getAllExpensesByCompany(
            @RequestHeader String token,
            @RequestParam Long companyId) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        List<EmployeeExpenseResponseDto> allExpenses = expenseService.getAllExpenseDtosByCompanyId(companyId);

        return ResponseEntity.ok(BaseResponse.<List<EmployeeExpenseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Şirkete ait tüm harcamalar başarıyla listelendi.")
                .data(allExpenses)
                .build());
    }


}
