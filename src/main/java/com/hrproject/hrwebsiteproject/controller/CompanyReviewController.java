package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyReviewCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyReviewResponseDto;
import com.hrproject.hrwebsiteproject.service.CompanyReviewService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.COMPANY_REVIEW)
@RequiredArgsConstructor
@CrossOrigin("*")
public class CompanyReviewController {

    private final CompanyReviewService reviewService;
    private final JwtManager jwtManager;


    @PostMapping(EndPoints.CREATE_COMPANY_REVIEW)
    public ResponseEntity<BaseResponse<CompanyReviewResponseDto>> createReview(
            @RequestHeader String token,
            @RequestBody @Valid CompanyReviewCreateRequestDto dto
    ) {
        Long userId = jwtManager.getUserIdFromToken(token);
        return ResponseEntity.ok(BaseResponse.<CompanyReviewResponseDto>builder()
                .code(200)
                .success(true)
                .message("Yorum başarıyla oluşturuldu. Yayınlanması için admin onayı bekleniyor.")
                .data(reviewService.createReview(dto, userId))
                .build());
    }

    @GetMapping(EndPoints.LIST_APPROVED_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> getAllApprovedReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Onaylı yorumlar listelendi.")
                .data(reviewService.getAllApprovedReviews())
                .build());
    }

    @GetMapping(EndPoints.LIST_PENDING_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> getPendingReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Onay bekleyen yorumlar listelendi.")
                .data(reviewService.getPendingReviews())
                .build());
    }

    @GetMapping(EndPoints.LIST_ALL_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<Page<CompanyReviewResponseDto>>> listAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        Page<CompanyReviewResponseDto> pagedReviews = reviewService.listAllReviews(pageable);

        return ResponseEntity.ok(BaseResponse.<Page<CompanyReviewResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Yorumlar sayfalandırılarak listelendi.")
                .data(pagedReviews)
                .build());
    }

    @PutMapping(EndPoints.APPROVE_OR_REJECT_COMPANY_REVIEW)
    public ResponseEntity<BaseResponse<Boolean>> approveOrRejectReview(
            @RequestParam Long reviewId,
            @RequestParam boolean approved,
            @RequestParam(required = false) String rejectionReason
    ) {
        reviewService.approveOrRejectReview(reviewId, approved, rejectionReason);
        String message = approved ? "Yorum onaylandı." : "Yorum reddedildi.";
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message(message)
                .data(true)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<Boolean>> deleteReview(
            @RequestHeader String token,
            @RequestParam Long id
    ) {
        Long userId = jwtManager.getUserIdFromToken(token);
        // (Opsiyonel) userId ile doğrulama yapılabilir.
        reviewService.deleteReview(id);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Yorum silindi.")
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.UPDATE_COMPANY_REVIEW)
    public ResponseEntity<BaseResponse<CompanyReviewResponseDto>> updateReview(
            @RequestHeader String token,
            @RequestParam Long reviewId,
            @RequestBody @Valid CompanyReviewCreateRequestDto dto
    ) {
        Long userId = jwtManager.getUserIdFromToken(token);
        CompanyReviewResponseDto updated = reviewService.updateReview(reviewId, dto, userId);
        return ResponseEntity.ok(BaseResponse.<CompanyReviewResponseDto>builder()
                .code(200)
                .success(true)
                .message("Yorum başarıyla güncellendi. Yayınlanması için admin onayı bekleniyor.")
                .data(updated)
                .build());
    }
}



