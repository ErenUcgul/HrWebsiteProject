package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyLeaveTypeRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveApprovalRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyLeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeLeaveResponseDto;
import com.hrproject.hrwebsiteproject.service.CompanyLeaveTypeService;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.EmployeeLeaveService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.COMPANY_LEAVE_TYPE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompanyLeaveController {

    private final CompanyLeaveTypeService companyLeaveTypeService;
    private final JwtManager jwtManager;
    private final EmployeeLeaveService employeeLeaveService;
    private final CompanyService companyService;

    @PostMapping(EndPoints.ASSIGN_COMPANY_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<Boolean>> assignLeaveType(
            @RequestHeader String token,
            @RequestBody @Valid CompanyLeaveTypeRequestDto dto) {

        companyLeaveTypeService.assignLeaveTypeToCompany(token, dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("İzin türü şirkete atandı.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.LIST_COMPANY_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<List<CompanyLeaveTypeResponseDto>>> listCompanyLeaveTypes(
            @RequestHeader String token) {

        List<CompanyLeaveTypeResponseDto> list = companyLeaveTypeService.getCompanyLeaveTypes(token);

        return ResponseEntity.ok(BaseResponse.<List<CompanyLeaveTypeResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Şirkete ait izin türleri listelendi.")
                .data(list)
                .build());
    }

    @PutMapping(EndPoints.APPROVE_OR_REJECT_LEAVE)
    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> handleLeaveRequest(
            @RequestHeader String token,
            @RequestBody @Valid LeaveApprovalRequestDto dto
    ) {
        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        EmployeeLeaveResponseDto response = employeeLeaveService.approveOrRejectLeave(dto, companyId);

        String message = dto.approved()
                ? "İzin talebi başarıyla onaylandı."
                : "İzin talebi başarıyla reddedildi.";

        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
                .code(200)
                .success(true)
                .message(message)
                .data(response)
                .build());
    }

}
