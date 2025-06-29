package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyDashboardDTO;
import com.hrproject.hrwebsiteproject.service.DashboardService;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.COMPANY)
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompanyController {

    private final EmployeeService employeeService;
    private final JwtManager jwtManager;
    private final DashboardService dashboardService;

    @GetMapping(EndPoints.COMPANY_DASHBOARD)
    public ResponseEntity<BaseResponse<CompanyDashboardDTO>> getCompanyDashboard(
            @RequestHeader String token) {

        Long companyId = jwtManager.getUserIdFromToken(token);

        CompanyDashboardDTO dashboard = dashboardService.getCompanyDashboard(companyId);

        return ResponseEntity.ok(BaseResponse.<CompanyDashboardDTO>builder()
                .code(200)
                .success(true)
                .message("Şirket dashboard verileri getirildi.")
                .data(dashboard)
                .build());
    }
}
