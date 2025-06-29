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

import java.util.List;

@RestController
@RequestMapping(EndPoints.EMPLOYEE_LEAVE_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeLeaveController {

    private final JwtManager jwtManager;
    private final CompanyService companyService;
    private final EmployeeLeaveService employeeLeaveService;
    private final EmployeeService employeeService;



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
//    @GetMapping(EndPoints.MY_LEAVES)
//    public ResponseEntity<BaseResponse<List<EmployeeLeaveResponseDto>>> getMyLeaves(@RequestHeader String token) {
//        Long userId = jwtManager.getUserIdFromToken(token);
//        Long employeeId = employeeService.getEmployeeIdByUserId(userId);
//
//        List<EmployeeLeaveResponseDto> leaves = employeeLeaveService.getLeavesByEmployeeId(employeeId);
//
//        return ResponseEntity.ok(BaseResponse.<List<EmployeeLeaveResponseDto>>builder()
//                .code(200)
//                .success(true)
//                .message("İzin talepleriniz listelendi.")
//                .data(leaves)
//                .build());
//    }
//
//
//    @PutMapping(EndPoints.UPDATE_LEAVE_REQUEST)
//    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> updateLeaveRequest(
//            @RequestHeader String token,
//            @RequestParam Long leaveRequestId,
//            @RequestBody @Valid EmployeeLeaveRequestDto dto) {
//
//        Long userId = jwtManager.getUserIdFromToken(token);
//        Long employeeId = employeeService.getEmployeeIdByUserId(userId);
//
//        EmployeeLeaveResponseDto updated = employeeLeaveService.updateLeaveRequest(leaveRequestId, dto, employeeId);
//
//        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
//                .code(200)
//                .success(true)
//                .message("İzin talebi güncellendi.")
//                .data(updated)
//                .build());
//    }
//
//    @DeleteMapping(EndPoints.CANCEL_LEAVE_REQUEST)
//    public ResponseEntity<BaseResponse<Boolean>> cancelLeaveRequest(
//            @RequestHeader String token,
//            @RequestParam Long leaveRequestId) {
//
//        Long userId = jwtManager.getUserIdFromToken(token);
//        Long employeeId = employeeService.getEmployeeIdByUserId(userId);
//
//        employeeLeaveService.cancelLeaveRequest(leaveRequestId, employeeId);
//
//        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
//                .code(200)
//                .success(true)
//                .message("İzin talebi iptal edildi.")
//                .data(true)
//                .build());
//    }

    @PostMapping(EndPoints.EMPLOYEE_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> requestLeave(
            @RequestHeader String token,
            @RequestBody @Valid EmployeeLeaveRequestDto dto
    ) {
        Long employeeId = jwtManager.getUserIdFromToken(token);
        Long companyId = jwtManager.getCompanyIdFromToken(token);

        EmployeeLeaveResponseDto response = employeeLeaveService.requestLeave(dto, employeeId, companyId);

        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
                .code(200)
                .success(true)
                .message("İzin talebi başarıyla oluşturuldu.")
                .data(response)
                .build());
    }

    @GetMapping(EndPoints.MY_LEAVES)
    public ResponseEntity<BaseResponse<List<EmployeeLeaveResponseDto>>> getMyLeaves(
            @RequestHeader String token) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        List<EmployeeLeaveResponseDto> leaves = employeeLeaveService.getLeavesByEmployeeId(employeeId);

        return ResponseEntity.ok(BaseResponse.<List<EmployeeLeaveResponseDto>>builder()
                .code(200)
                .success(true)
                .message("İzin talepleri listelendi.")
                .data(leaves)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<EmployeeLeaveResponseDto>> updateLeaveRequest(
            @RequestHeader String token,
            @RequestParam Long leaveRequestId,
            @RequestBody @Valid EmployeeLeaveRequestDto dto) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        EmployeeLeaveResponseDto updated = employeeLeaveService.updateLeaveRequest(leaveRequestId, dto, employeeId);

        return ResponseEntity.ok(BaseResponse.<EmployeeLeaveResponseDto>builder()
                .code(200)
                .success(true)
                .message("İzin talebi güncellendi.")
                .data(updated)
                .build());
    }

    @DeleteMapping(EndPoints.CANCEL_LEAVE_REQUEST)
    public ResponseEntity<BaseResponse<Boolean>> cancelLeaveRequest(
            @RequestHeader String token,
            @RequestParam Long leaveRequestId) {

        Long employeeId = jwtManager.getUserIdFromToken(token);
        employeeLeaveService.cancelLeaveRequest(leaveRequestId, employeeId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("İzin talebi iptal edildi.")
                .data(true)
                .build());
    }

}
