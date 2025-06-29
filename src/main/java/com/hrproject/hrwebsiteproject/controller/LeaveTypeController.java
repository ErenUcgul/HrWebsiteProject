package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.LeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.service.EmployeeService;
import com.hrproject.hrwebsiteproject.service.LeaveTypeService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.LEAVE_TYPE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;
    private final JwtManager jwtManager;

    @PostMapping(EndPoints.CREATE_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<LeaveTypeResponseDto>> create(
            @RequestHeader String token,
            @RequestBody @Valid LeaveTypeCreateRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);


        LeaveTypeResponseDto created = leaveTypeService.create(dto);
        return ResponseEntity.ok(BaseResponse.<LeaveTypeResponseDto>builder()
                .code(200)
                .success(true)
                .message("İzin türü başarıyla oluşturuldu.")
                .data(created)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<LeaveTypeResponseDto>> update(
            @RequestHeader String token,
            @RequestParam Long id,
            @RequestBody @Valid LeaveTypeUpdateRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);


        LeaveTypeResponseDto updated = leaveTypeService.update(id, dto);
        return ResponseEntity.ok(BaseResponse.<LeaveTypeResponseDto>builder()
                .code(200)
                .success(true)
                .message("İzin türü başarıyla güncellendi.")
                .data(updated)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @RequestHeader String token,
            @RequestParam Long id) {

        Long userId = jwtManager.getUserIdFromToken(token);


        leaveTypeService.delete(id);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("İzin türü başarıyla silindi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.LIST_LEAVE_TYPE)
    public ResponseEntity<BaseResponse<List<LeaveTypeResponseDto>>> list(@RequestHeader String token) {
        Long userId = jwtManager.getUserIdFromToken(token);
        List<LeaveTypeResponseDto> list = leaveTypeService.findAll();
        return ResponseEntity.ok(BaseResponse.<List<LeaveTypeResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Tüm izin türleri başarıyla listelendi.")
                .data(list)
                .build());
    }

}
