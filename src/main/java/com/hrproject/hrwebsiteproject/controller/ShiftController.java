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
            @RequestBody @Valid List<ShiftRequestDto> dtoList) {
        Boolean result = shiftService.createAndCheckShift(dtoList);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya oluşturma işlemi tamamlandı.")
                .data(result)
                .build());
    }

    @GetMapping(EndPoints.LIST_SHIFTS)
    public ResponseEntity<BaseResponse<List<ShiftResponseDto>>> getShiftsByCompany(@RequestParam Long companyId) {
        List<ShiftResponseDto> responseList = shiftService.getShiftsByCompanyId(companyId);

        return ResponseEntity.ok(BaseResponse.<List<ShiftResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Şirkete ait vardiyalar başarıyla getirildi.")
                .data(responseList)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> deleteShift(Long shiftId) {
        Boolean result = shiftService.deleteShift(shiftId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarıyla silindi.")
                .data(result)
                .build());
    }
    @PutMapping(EndPoints.UPDATE_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> updateShift(Long shiftId, @RequestBody ShiftUpdateRequestDto dto) {
        Boolean result = shiftService.updateShift(shiftId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarıyla güncellendi.")
                .data(result)
                .build());
    }
    @PostMapping(EndPoints.ASSIGN_SHIFT)
    public ResponseEntity<BaseResponse<Boolean>> assignShift(Long employeeId, @RequestBody @Valid AssignShiftRequestDto dto) {
        Boolean result = shiftTrackingService.assignShiftToEmployee(employeeId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya başarılı bir şekilde çalışana atandı.")
                .data(result)
                .build());
    }
    @PutMapping(EndPoints.UPDATE_SHIFT_ASSIGN)
    public ResponseEntity<BaseResponse<Boolean>> updateAssignedShift(Long trackingId,@RequestBody @Valid AssignShiftRequestDto dto
    ) {
        Boolean result = shiftTrackingService.updateAssignedShift(trackingId, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya ataması güncellendi.")
                .data(result)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_SHIFT_ASSIGN)
    public ResponseEntity<BaseResponse<Boolean>> deleteAssignedShift(Long trackingId) {
        Boolean result = shiftTrackingService.deleteAssignedShift(trackingId);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .code(200)
                .message("Vardiya ataması başarıyla silindi.")
                .data(result)
                .build());
    }
    @GetMapping(EndPoints.GET_SHIFT_BY_EMPLOYEE)
    public ResponseEntity<BaseResponse<List<EmployeeShiftResponseDto>>> getShiftsByEmployeeId(Long id) {
        List<EmployeeShiftResponseDto> response = shiftTrackingService.getShiftsByEmployeeId(id);
        return ResponseEntity.ok(BaseResponse.<List<EmployeeShiftResponseDto>>builder()
                .success(true)
                .code(200)
                .message("Çalışanın vardiya listesi başarıyla getirildi.")
                .data(response)
                .build());
    }
    @GetMapping(EndPoints.GET_EMPLOYEE_BY_SHIFT)
    public ResponseEntity<BaseResponse<List<EmployeeShiftResponseDto>>> getEmployeesByShiftId(Long id) {
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
