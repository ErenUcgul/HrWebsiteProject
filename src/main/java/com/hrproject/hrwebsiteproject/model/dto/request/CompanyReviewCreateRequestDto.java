package com.hrproject.hrwebsiteproject.model.dto.request;

public record CompanyReviewCreateRequestDto(
        Long companyId, // denemeden sonra kaldırılacak tokendan çekilecek
        String content
) {
}
