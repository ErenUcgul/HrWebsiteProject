package com.hrproject.hrwebsiteproject.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ResetPasswordRequestDto(
        @NotBlank(message = "E-posta alanı boş olamaz.")
        @Email(message = "Geçerli bir e-posta adresi giriniz.")
        String email,
        @Pattern(regexp = "^.*(?=.{8,64})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*.]).*$",
                message = "Şifre en az 8 karakter, en fazla 64 karakter, 1 Büyük harf 1 " +
                        "Küçük harf, 1 Rakam ve 1 Özel karakter olmalıdır.")
        @NotBlank(message = "Yeni şifre boş olamaz.")
        String newPassword,

        @NotBlank(message = "Şifre tekrarı boş olamaz.")
        String rePassword,

        @NotBlank(message = "Şifre sıfırlama kodu boş olamaz.")
        String resetCode
) {
}
