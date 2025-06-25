package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyReviewCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyReviewResponseDto;
import com.hrproject.hrwebsiteproject.service.CompanyReviewService;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @RequestParam Long userId,
            @RequestBody @Valid CompanyReviewCreateRequestDto dto
    ) {
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
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> listAllReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Tüm yorumlar listelendi.")
                .data(reviewService.listAllReviews())
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
    public ResponseEntity<BaseResponse<Boolean>> deleteReview(@RequestParam Long id) {
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
            @RequestParam Long reviewId,
            @RequestParam Long userId,
            @RequestBody @Valid CompanyReviewCreateRequestDto dto
    ) {
        CompanyReviewResponseDto updated = reviewService.updateReview(reviewId, dto, userId);
        return ResponseEntity.ok(BaseResponse.<CompanyReviewResponseDto>builder()
                .code(200)
                .success(true)
                .message("Yorum başarıyla güncellendi. Yayınlanması için admin onayı bekleniyor.")
                .data(updated)
                .build());
    }
}


/*
    @PostMapping(EndPoints.CREATE_COMPANY_REVIEW)
    public ResponseEntity<BaseResponse<CompanyReviewResponseDto>> createReview(
            @RequestHeader String token,
            @RequestBody @Valid CompanyReviewCreateRequestDto dto
    ) {
        Long userId = jwtManager.getUserIdFromToken(token);
        return ResponseEntity.ok(BaseResponse.<CompanyReviewResponseDto>builder()
                .success(true)
                .message("Yorum başarıyla oluşturuldu. Yayınlanması için admin onayı bekleniyor.")
                .data(reviewService.createReview(dto, userId))
                .build());
    }

    @GetMapping(EndPoints.LIST_APPROVED_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> getAllApprovedReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .success(true)
                .message("Onaylı yorumlar listelendi.")
                .data(reviewService.getAllApprovedReviews())
                .build());
    }

    @GetMapping(EndPoints.LIST_PENDING_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> getPendingReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .success(true)
                .message("Onay bekleyen yorumlar listelendi.")
                .data(reviewService.getPendingReviews())
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
                .success(true)
                .message(message)
                .data(true)
                .build());
    }

    @DeleteMapping(EndPoints.DELETE_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<Boolean>> deleteReview(@RequestParam Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
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
                .success(true)
                .message("Yorum başarıyla güncellendi. Yayınlanması için admin onayı bekleniyor.")
                .data(updated)
                .build());
    }
    @GetMapping(EndPoints.LIST_ALL_COMPANY_REVIEWS)
    public ResponseEntity<BaseResponse<List<CompanyReviewResponseDto>>> listAllReviews() {
        return ResponseEntity.ok(BaseResponse.<List<CompanyReviewResponseDto>>builder()
                .success(true)
                .message("Tüm yorumlar listelendi.")
                .data(reviewService.listAllReviews())
                .build());
    }

 */


