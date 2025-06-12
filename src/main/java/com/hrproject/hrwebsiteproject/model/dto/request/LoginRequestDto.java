package com.hrproject.hrwebsiteproject.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Email boş olamaz") String email,
        @NotBlank(message = "Şifre boş olamaz") String password
) {
}
