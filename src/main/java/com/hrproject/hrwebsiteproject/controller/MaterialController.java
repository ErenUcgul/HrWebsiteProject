package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.MaterialRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.MaterialUpdateStatusRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.MaterialResponseDto;
import com.hrproject.hrwebsiteproject.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.MATERIAL)
@RequiredArgsConstructor
@CrossOrigin("*")
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping(EndPoints.CREATE_MATERIAL)
    public ResponseEntity<BaseResponse<Boolean>> createMaterial(@RequestBody @Valid MaterialRequestDto dto) {
        materialService.createMaterial(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Materyal başarıyla eklendi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.LIST_MATERIAL)
    public ResponseEntity<BaseResponse<List<MaterialResponseDto>>> getAllMaterials() {
        List<MaterialResponseDto> materials = materialService.getAllActiveMaterials();
        return ResponseEntity.ok(BaseResponse.<List<MaterialResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Materyal listesi başarıyla getirildi.")
                .data(materials)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_MATERIAL_STATUS)
    public ResponseEntity<BaseResponse<Boolean>> updateMaterialStatus(
            @RequestBody @Valid MaterialUpdateStatusRequestDto dto) {
        materialService.updateMaterialStatus(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Materyal durumu başarıyla güncellendi.")
                .data(true)
                .build());
    }
}
