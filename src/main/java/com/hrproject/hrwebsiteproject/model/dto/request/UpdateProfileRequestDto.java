package com.hrproject.hrwebsiteproject.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequestDto(
        String firstName,
        String lastName,
        String phone,
        String avatar
) {
}
