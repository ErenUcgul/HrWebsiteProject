package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyDashboardResponse;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.COMPANY)
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompanyController {

    private final EmployeeService employeeService;
    private final JwtManager jwtManager;

    @GetMapping(EndPoints.COMPANY_DASHBOARD)
    public ResponseEntity<BaseResponse<String>> getCompanyDashboard() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .data("Company dashboard verisi şu anda boş.")
                .message("Şirket yöneticisi dashboard verisi başarıyla getirildi.")
                .build());
    }


}
