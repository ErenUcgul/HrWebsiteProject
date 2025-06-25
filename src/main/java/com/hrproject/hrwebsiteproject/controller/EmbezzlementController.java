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
            @RequestBody @Valid AddEmbezzlementRequestDto dto) {

        embezzlementService.addEmbezzlement(dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Materyal başarıyla zimmete eklendi.")
                .data(true)
                .build());
    }

    @GetMapping(EndPoints.GET_EMBEZZLEMENT_LIST)
    public ResponseEntity<BaseResponse<List<EmbezzlementResponseDto>>> getEmbezzlementList(
            @RequestHeader String token) {

        List<EmbezzlementResponseDto> list = embezzlementService.getEmbezzlementList(token);

        return ResponseEntity.ok(BaseResponse.<List<EmbezzlementResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Zimmet listesi başarıyla getirildi.")
                .data(list)
                .build());
    }

    @PutMapping(EndPoints.ASSIGMENT_EMBEZZLEMENT)
    public ResponseEntity<BaseResponse<Boolean>> assignEmbezzlement(
            @RequestBody @Valid AssigmentEmbezzlementRequestDto dto) {

        embezzlementService.assignEmbezzlement(dto);

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
            @RequestBody @Valid DeleteEmbezzlementRequestDto dto) {

        embezzlementService.deleteEmbezzlementByUser(dto);

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Zimmet kaydı başarıyla kaldırıldı.")
                .data(true)
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

}

