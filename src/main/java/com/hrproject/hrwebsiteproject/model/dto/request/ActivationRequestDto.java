package com.hrproject.hrwebsiteproject.model.dto.request;

public record ActivationRequestDto(
        String email,
        String code
) {
}
