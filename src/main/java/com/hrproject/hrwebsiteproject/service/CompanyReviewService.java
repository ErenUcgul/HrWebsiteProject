package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.CompanyReviewMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyReviewCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyReviewResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.entity.CompanyReview;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EReviewStatus;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.repository.CompanyReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;
    private final CompanyService companyService;
    private final UserService userService;
    private final CompanyReviewMapper companyReviewMapper;

    public CompanyReviewResponseDto createReview(CompanyReviewCreateRequestDto dto, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        if (!user.getUserRole().equals(EUserRole.MANAGER)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        Company company = companyService.findByUserId(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        companyReviewRepository.findByCompanyIdAndManagerUserId(company.getId(), userId)
                .ifPresent(r -> {
                    throw new HrWebsiteProjectException(ErrorType.REVIEW_ALREADY_EXISTS);
                });

        CompanyReview review = companyReviewMapper.toEntity(dto);
        review.setCompanyId(company.getId());
        review.setManagerUserId(userId);
        review.setReviewStatus(EReviewStatus.PENDING); // artık enum ile durum belirleniyor
        review.setAvatar(dto.avatar());
        review.setRejectionReason(null);

        companyReviewRepository.save(review);

        return companyReviewMapper.toDto(review, company, user);
    }

    public List<CompanyReviewResponseDto> getAllApprovedReviews() {
        return companyReviewRepository.findAllByReviewStatus(EReviewStatus.APPROVED).stream()
                .map(review -> {
                    Company company = companyService.findById(review.getCompanyId())
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
                    User user = userService.findById(review.getManagerUserId())
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
                    return companyReviewMapper.toDto(review, company, user);
                }).toList();
    }

    public List<CompanyReviewResponseDto> getPendingReviews() {
        return companyReviewRepository.findAllByReviewStatus(EReviewStatus.PENDING).stream()
                .map(review -> {
                    Company company = companyService.findById(review.getCompanyId())
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
                    User user = userService.findById(review.getManagerUserId())
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
                    return companyReviewMapper.toDto(review, company, user);
                }).toList();
    }

    public void approveOrRejectReview(Long reviewId, boolean approved, String rejectionReason) {
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.REVIEW_NOT_FOUND));

        if (approved) {
            review.setReviewStatus(EReviewStatus.APPROVED);
            review.setRejectionReason(null);
        } else {
            review.setReviewStatus(EReviewStatus.REJECTED);
            review.setRejectionReason(rejectionReason != null ? rejectionReason : "Yorum reddedildi.");
        }

        companyReviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        companyReviewRepository.deleteById(reviewId);
    }

    public CompanyReviewResponseDto updateReview(Long reviewId, CompanyReviewCreateRequestDto dto, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        if (!user.getUserRole().equals(EUserRole.MANAGER)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        Company company = companyService.findByUserId(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        CompanyReview existingReview = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.REVIEW_NOT_FOUND));

        if (!existingReview.getCompanyId().equals(company.getId()) ||
                !existingReview.getManagerUserId().equals(userId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        if (existingReview.getReviewStatus() == EReviewStatus.APPROVED) {
            throw new HrWebsiteProjectException(ErrorType.REVIEW_ALREADY_APPROVED);
        }

        existingReview.setContent(dto.content());
        existingReview.setReviewStatus(EReviewStatus.PENDING);
        existingReview.setRejectionReason(null);

        companyReviewRepository.save(existingReview);

        return companyReviewMapper.toDto(existingReview, company, user);
    }

    //    public List<CompanyReviewResponseDto> listAllReviews() {
//        List<CompanyReview> reviews = companyReviewRepository.findAll();
//        return reviews.stream()
//                .map(review -> {
//                    Company company = companyService.findById(review.getCompanyId())
//                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
//                    User user = userService.findById(review.getManagerUserId())
//                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
//                    return companyReviewMapper.toDto(review, company, user);
//                })
//                .toList();
//    }
    public Page<CompanyReviewResponseDto> listAllReviews(Pageable pageable) {
        Page<CompanyReview> reviews = companyReviewRepository.findAll(pageable);

        return reviews.map(review -> {
            Company company = companyService.findById(review.getCompanyId())
                    .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
            User user = userService.findById(review.getManagerUserId())
                    .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
            return companyReviewMapper.toDto(review, company, user);
        });
    }
}
