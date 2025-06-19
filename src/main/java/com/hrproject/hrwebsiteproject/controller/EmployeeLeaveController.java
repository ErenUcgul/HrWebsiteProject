package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeLeaveRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeLeaveResponseDto;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.EmployeeLeaveService;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.EMPLOYEE_LEAVE_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeLeaveController {

    private final JwtManager jwtManager;
    private final CompanyService companyService;
    private final EmployeeLeaveService employeeLeaveService;
    private final EmployeeService employeeService;

    @PostMapping(EndPoints.EMPLOYEE_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> requestLeave(
            @RequestParam Long employeeId,
            @RequestParam Long companyId,
            @RequestBody @Valid EmployeeLeaveRequestDto dto
    ) {
        EmployeeLeaveResponseDto response = employeeLeaveService.requestLeave(dto, employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
                .code(200)
                .success(true)
                .message("İzin talebi başarıyla oluşturuldu.")
                .data(response)
                .build());
    }

    //TODO: Employee girişi sonrası token checkli metod kullanılacak
//    @PostMapping(EndPoints.EMPLOYEE_LEAVE_REQUEST)
//    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> requestLeave(
//            @RequestHeader String token,
//            @RequestBody @Valid EmployeeLeaveRequestDto dto
//    ) {
//        Long userId = jwtManager.getUserIdFromToken(token);
//
//        Long employeeId = employeeService.getEmployeeIdByUserId(userId);
//
//        // 2. Şirkete ait izin talebi yapılıyor
//        Long companyId = companyService.getCompanyIdByUserId(userId);
//
//        // 3. Servise istek gönderiliyor
//        EmployeeLeaveResponseDto response = employeeLeaveService.requestLeave(dto, employeeId, companyId);
//
//        // 4. Başarılı dönüş
//        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
//                .code(200)
//                .success(true)
//                .message("İzin talebi başarıyla oluşturuldu.")
//                .data(response)
//                .build());
//    }

}
