package com.hrproject.hrwebsiteproject.model.dto.request;

public record VerifyEmailChangeRequestDto(
        String email, String code
) {
}
