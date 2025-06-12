package com.hrproject.hrwebsiteproject.model.dto.response;

public record LoginResponseDto
        (
        String accessToken,
        String refreshToken
        ) {
}
