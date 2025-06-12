package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.EMPLOYEE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping(EndPoints.EMPLOYEE_DASHBOARD)
    public ResponseEntity<BaseResponse<String>> getEmployeeDashboard() {
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .data("Employee dashboard verisi şu anda boş.")
                .message("Personel dashboard verisi başarıyla getirildi.")
                .build());
    }

}
