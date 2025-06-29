package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.AddEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.AssigmentEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.DeleteEmbezzlementRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.EmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.PersonalEmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.service.EmbezzlementService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.EMBEZZLEMENT)
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmbezzlementController {

    private final EmbezzlementService embezzlementService;
    private final JwtManager jwtManager;

    @PostMapping(EndPoints.ADD_EMBEZZLEMENT)
    public ResponseEntity<BaseResponse<Boolean>> addEmbezzlement(
            @RequestHeader String token,
            @RequestBody @Valid AddEmbezzlementRequestDto dto) {

        Long companyId = jwtManager.getUserIdFromToken(token);
        embezzlementService.addEmbezzlement(dto, companyId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Materyal başarıyla zimmete eklendi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.GET_ACTIVE_EMBEZZLEMENT_LIST)
    public ResponseEntity<BaseResponse<List<EmbezzlementResponseDto>>> getEmbezzlementList(
            @RequestHeader String token) {

        List<EmbezzlementResponseDto> list = embezzlementService.getActiveEmbezzlementList(token);

        return ResponseEntity.ok(BaseResponse.<List<EmbezzlementResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Aktif zimmet listesi başarıyla getirildi.")
                .data(list)
                .build());
    }
    @GetMapping(EndPoints.GET_PASSIVE_EMBEZZLEMENT_LIST)
    public ResponseEntity<BaseResponse<List<EmbezzlementResponseDto>>> getPassiveEmbezzlementList(
            @RequestHeader String token) {

        List<EmbezzlementResponseDto> list = embezzlementService.getPassiveEmbezzlementList(token);

        return ResponseEntity.ok(BaseResponse.<List<EmbezzlementResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Pasif zimmet listesi başarıyla getirildi.")
                .data(list)
                .build());
    }

    @PutMapping(EndPoints.ASSIGMENT_EMBEZZLEMENT)
    public ResponseEntity<BaseResponse<Boolean>> assignEmbezzlement(
            @RequestHeader String token,
            @RequestBody @Valid AssigmentEmbezzlementRequestDto dto) {

        Long managerId = jwtManager.getUserIdFromToken(token);
        embezzlementService.assignEmbezzlement(dto, managerId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Zimmet ataması başarıyla yapıldı.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.GET_ALL_MY_EMBEZZLEMENT_LIST)
    public ResponseEntity<BaseResponse<List<PersonalEmbezzlementResponseDto>>> getMyEmbezzlementList(
            @RequestHeader String token) {

        List<PersonalEmbezzlementResponseDto> list = embezzlementService.getMyEmbezzlementList(token);

        return ResponseEntity.ok(BaseResponse.<List<PersonalEmbezzlementResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Size ait zimmet listesi başarıyla getirildi.")
                .data(list)
                .build());
    }

    @PutMapping(EndPoints.DELETE_EMBEZZLEMENT_BY_USERID)
    public ResponseEntity<BaseResponse<Boolean>> deleteEmbezzlementByUser(
            @RequestHeader String token,
            @RequestBody @Valid DeleteEmbezzlementRequestDto dto) {

        Long userId = jwtManager.getUserIdFromToken(token);
        embezzlementService.deleteEmbezzlementByUser(dto, userId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Zimmet kaydı başarıyla kaldırıldı.")
                .data(true)
                .build());
    }
    @PutMapping(EndPoints.DELETE_EMBEZZLEMENT_BY_ID)
    public ResponseEntity<BaseResponse<String>> deleteEmbezzlementById(
            @RequestParam Long embezzlementId,
            @RequestParam String token) {

        embezzlementService.deleteEmbezzlementById(embezzlementId, token);

        return ResponseEntity.ok(BaseResponse.<String>builder()
                .success(true)
                .code(200)
                .message("Zimmet silindi.")
                .data("Embezzlement ID: " + embezzlementId + " silindi.")
                .build());
    }

    @PutMapping(EndPoints.RETURN_EMBEZZLEMENT)
    public ResponseEntity<BaseResponse<Boolean>> returnEmbezzlement(
            @RequestParam Long embezzlementId,
            @RequestHeader String token) {

        Long userId = jwtManager.getUserIdFromToken(token);
        embezzlementService.returnEmbezzlement(embezzlementId, userId);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .success(true)
                .message("Zimmet başarıyla iade edildi.")
                .code(200)
                .data(true)
                .build());
    }
    @GetMapping(EndPoints.GET_ALL_EMBEZZLEMENT_LIST_BY_COMPANY)
    public ResponseEntity<BaseResponse<List<EmbezzlementResponseDto>>> getPersonalEmbezzlementList(
            @RequestHeader String token) {

        List<EmbezzlementResponseDto> list = embezzlementService.getAllEmbezzlementListByManager(token);

        return ResponseEntity.ok(BaseResponse.<List<EmbezzlementResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Tüm zimmetler başarıyla getirildi.")
                .data(list)
                .build());
    }
}
