package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyLeaveTypeRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveApprovalRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyLeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeLeaveResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.SalaryPaymentResponseDto;
import com.hrproject.hrwebsiteproject.model.enums.ESalaryStatus;
import com.hrproject.hrwebsiteproject.service.CompanyLeaveTypeService;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.EmployeeLeaveService;
import com.hrproject.hrwebsiteproject.service.SalaryPaymentService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping(EndPoints.SALARY_PAYMENT_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class SalaryPaymentController {

    private final SalaryPaymentService salaryPaymentService;

    @PostMapping(EndPoints.GENERATE_SALARY)
    public ResponseEntity<BaseResponse<SalaryPaymentResponseDto>> generateSalary(
            @RequestParam Long employeeId,
            @RequestParam Long companyId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {

        SalaryPaymentResponseDto dto = salaryPaymentService.createMonthlySalaryPayment(employeeId, companyId, month);

        return ResponseEntity.ok(BaseResponse.<SalaryPaymentResponseDto>builder()
                .code(200)
                .success(true)
                .message("Bordro başarıyla oluşturuldu.")
                .data(dto)
                .build());
    }

}
