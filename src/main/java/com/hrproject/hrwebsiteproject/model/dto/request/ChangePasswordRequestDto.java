package com.hrproject.hrwebsiteproject.model.dto.request;

public record ChangePasswordRequestDto(
        String oldPassword,
        String newPassword,
        String reNewPassword
) {
}
