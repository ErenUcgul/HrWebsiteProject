package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.AssignShiftRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ShiftRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ShiftUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeShiftResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.MyShiftsResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.ShiftResponseDto;
import com.hrproject.hrwebsiteproject.service.ShiftService;
import com.hrproject.hrwebsiteproject.service.ShiftTrackingService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.SHIFT)
public class ShiftController {
    private final JwtManager jwtManager;
    private final ShiftService shiftService;
    private final ShiftTrackingService shiftTrackingService;

    @PostMapping(EndPoints.CREATE_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> createShift(
            @RequestHeader String token,
            @RequestBody @Valid List<ShiftRequestDto> dtoList) {

        Long userId = jwtManager.getUserIdFromToken(token);
        // İstersen userId veya companyId ile yetki kontrolü yapılabilir

        Boolean result = shiftService.createAndCheckShift(dtoList);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya oluşturma işlemi tamamlandı.")
                .data(result)
                .build());
    }

    @GetMapping(EndPoints.LIST_SHIFTS)
    public ResponseEntity<BaseResponse<List<ShiftResponseDto>>> getShiftsByCompany(
            @RequestHeader String token,
            @RequestParam Long companyId) {

        Long userId = jwtManager.getUserIdFromToken(token);
        // Yetki kontrolü yapılabilir

        List<ShiftResponseDto> responseList = shiftService.getShiftsByCompanyId(companyId);
        return ResponseEntity.ok(BaseResponse.<List<ShiftResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Şirkete ait vardiyalar başarıyla getirildi.")
                .data(responseList)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> deleteShift(
            @RequestHeader String token,
            @RequestParam Long shiftId) {

        Long userId = jwtManager.getUserIdFromToken(token);

        Boolean result = shiftService.deleteShift(shiftId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarıyla silindi.")
                .data(result)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> updateShift(
            @RequestHeader String token,
            @RequestParam Long shiftId,
            @RequestBody ShiftUpdateRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);

        Boolean result = shiftService.updateShift(shiftId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarıyla güncellendi.")
                .data(result)
                .build());
    }

    @PostMapping(EndPoints.ASSIGN_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> assignShift(
            @RequestHeader String token,
            @RequestParam Long employeeId,
            @RequestBody @Valid AssignShiftRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);

        Boolean result = shiftTrackingService.assignShiftToEmployee(employeeId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarılı bir şekilde çalışana atandı.")
                .data(result)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_SHIFT_ASSIGN)
    public ResponseEntity<BaseResponse<Boolean>> updateAssignedShift(
            @RequestHeader String token,
            @RequestParam Long trackingId,
            @RequestBody @Valid AssignShiftRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);

        Boolean result = shiftTrackingService.updateAssignedShift(trackingId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya ataması güncellendi.")
                .data(result)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_SHIFT_ASSIGN)
    public ResponseEntity<BaseResponse<Boolean>> deleteAssignedShift(
            @RequestHeader String token,
            @RequestParam Long trackingId) {

        Long userId = jwtManager.getUserIdFromToken(token);

        Boolean result = shiftTrackingService.deleteAssignedShift(trackingId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya ataması başarıyla silindi.")
                .data(result)
                .build());
    }

    @GetMapping(EndPoints.GET_SHIFT_BY_EMPLOYEE)
    public ResponseEntity<BaseResponse<List<EmployeeShiftResponseDto>>> getShiftsByEmployeeId(
            @RequestHeader String token,
            @RequestParam Long id) {

        Long userId = jwtManager.getUserIdFromToken(token);

        List<EmployeeShiftResponseDto> response = shiftTrackingService.getShiftsByEmployeeId(id);
        return ResponseEntity.ok(BaseResponse.<List<EmployeeShiftResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Çalışanın vardiya listesi başarıyla getirildi.")
                .data(response)
                .build());
    }

    @GetMapping(EndPoints.GET_EMPLOYEE_BY_SHIFT)
    public ResponseEntity<BaseResponse<List<EmployeeShiftResponseDto>>> getEmployeesByShiftId(
            @RequestHeader String token,
            @RequestParam Long id) {

        Long userId = jwtManager.getUserIdFromToken(token);

        List<EmployeeShiftResponseDto> response = shiftTrackingService.getEmployeesByShiftId(id);
        return ResponseEntity.ok(BaseResponse.<List<EmployeeShiftResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Vardiyaya atanan personeller başarıyla getirildi.")
                .data(response)
                .build());
    }

    @GetMapping(EndPoints.GET_MY_SHIFTS)
    public ResponseEntity<BaseResponse<List<MyShiftsResponseDto>>> getOwnShiftAssignments(
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        List<MyShiftsResponseDto> shifts = shiftTrackingService.getMyShiftsList(userId);

        return ResponseEntity.ok(BaseResponse.<List<MyShiftsResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Kullanıcının vardiya listesi başarıyla getirildi.")
                .data(shifts)
                .build());
    }
}
