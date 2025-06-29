package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.*;
import com.hrproject.hrwebsiteproject.service.CompanyService;
import com.hrproject.hrwebsiteproject.service.DashboardService;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.EMPLOYEE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final JwtManager jwtManager;
    private final CompanyService companyService;
    private final DashboardService dashboardService;

    @GetMapping(EndPoints.EMPLOYEE_DASHBOARD)
    public ResponseEntity<BaseResponse<EmployeeDashboardDTO>> getDashboard(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        EmployeeDashboardDTO dashboard = dashboardService.getEmployeeDashboard(employeeId);

        return ResponseEntity.ok(BaseResponse.<EmployeeDashboardDTO>builder()
                .code(200)
                .success(true)
                .message("Employee dashboard verileri getirildi.")
                .data(dashboard)
                .build());
    }

    @PostMapping(EndPoints.CREATE_EMPLOYEE)
    public ResponseEntity<BaseResponse<Boolean>> createEmployee(
            @RequestBody @Valid EmployeeCreateRequestDto dto,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        employeeService.createEmployee(dto, companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Personel başarıyla oluşturuldu.")
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_EMPLOYEE)
    public ResponseEntity<BaseResponse<Boolean>> updateEmployee(
            Long employeeId,
            @RequestBody @Valid EmployeeUpdateRequestDto dto,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        employeeService.updateEmployee(employeeId, companyId, dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .message("Personel başarıyla güncellendi.")
                .code(200)
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.DELETE_EMPLOYEE)
    public ResponseEntity<BaseResponse<Boolean>> softDeleteEmployee(
            @RequestParam Long employeeId,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        employeeService.softDeleteEmployee(employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .message("Personel başarıyla silindi (soft delete).")
                .code(200)
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.ACTIVATE_EMPLOYEE)
    public ResponseEntity<BaseResponse<Boolean>> activateEmployee(
            @RequestParam Long employeeId,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        employeeService.activateEmployee(employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .message("Personel başarıyla aktifleştirildi.")
                .code(200)
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.DEACTIVATE_EMPLOYEE)
    public ResponseEntity<BaseResponse<Boolean>> deactivateEmployee(
            @RequestParam Long employeeId,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        employeeService.deactivateEmployee(employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .message("Personel başarıyla pasifleştirildi.")
                .code(200)
                .data(true)
                .build());
    }


    @GetMapping(EndPoints.LIST_ALL_EMPLOYEES)
    public ResponseEntity<BaseResponse<List<EmployeeListDto>>> getAllEmployees(
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        List<EmployeeListDto> employees = employeeService.getAllEmployees(companyId);

        return ResponseEntity.ok(BaseResponse.<List<EmployeeListDto>>builder()
                .code(200)
                .success(true)
                .message("Tüm çalışanlar listelendi.")
                .data(employees)
                .build());
    }

    @GetMapping(EndPoints.GET_EMPLOYEE_DETAILS)
    public ResponseEntity<BaseResponse<EmployeeDetailDto>> getEmployeeDetails(
            @RequestHeader String token,
            Long employeeId) {

        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = companyService.getCompanyIdByUserId(userId);

        EmployeeDetailDto detailsDto = employeeService.getEmployeeDetails(employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<EmployeeDetailDto>builder()
                .code(200)
                .success(true)
                .message("Çalışan detay bilgisi getirildi.")
                .data(detailsDto)
                .build());
    }
}
