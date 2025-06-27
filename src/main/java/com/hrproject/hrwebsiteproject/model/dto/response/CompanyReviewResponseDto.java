package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EReviewStatus;

public record CompanyReviewResponseDto(

        String companyName,
        String managerFullName,
        String reviewContent,
        EReviewStatus reviewStatus,
        String rejectionReason,
        String avatar
) {
}
