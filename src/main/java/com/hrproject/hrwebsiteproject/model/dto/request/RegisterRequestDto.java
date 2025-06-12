package com.hrproject.hrwebsiteproject.model.dto.request;

import com.hrproject.hrwebsiteproject.model.enums.ECompanyType;
import com.hrproject.hrwebsiteproject.model.enums.ERegion;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "Adınız boş bırakılamaz.")
        @Size(min = 2, max = 30, message = "Adınız min=2, max=30 karakter olmalıdır.")
        String firstName,
        @NotBlank(message = "Soyadınız boş bırakılamaz.")
        @Size(min = 2, max = 30, message = "Soyadınız min=2, max=30 karakter olmalıdır.")
        String lastName,
        @NotBlank
        @Pattern(regexp = "^.*(?=.{8,64})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*.]).*$",
                message = "Şifre en az 8 karakter, en fazla 64 karakter, 1 Büyük harf 1 " +
                        "Küçük harf, 1 Rakam ve 1 Özel karakter olmalıdır.")
        String password,
        @NotBlank(message = "Lütfen şifrenizi tekrar giriniz.")
        String rePassword,
        @NotBlank
        @Email
        String email,
        String phone,
        String avatar,
        Egender gender,

        //company
        //TODO:VALİDASYON CHECKLER KONTROL EDİLECEK
        String companyName,
        String companyAddress,
        ERegion region,
        String companyPhone,
        String companyEmail,
        String companyLogo,
        ECompanyType companyType
//        EUserRole userRole

) {
}
