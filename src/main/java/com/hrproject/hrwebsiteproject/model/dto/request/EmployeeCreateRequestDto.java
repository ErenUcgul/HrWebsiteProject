package com.hrproject.hrwebsiteproject.model.dto.request;

import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EPosition;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequestDto {

    // User bilgileri
    @NotBlank(message = "Ad boş olamaz.")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz.")
    private String lastName;

    @NotBlank(message = "Telefon numarası boş olamaz.")
    private String phone;
    @NotBlank
    @Email
    private String email;
    private String avatar;
    @NotBlank
    @Pattern(regexp = "^.*(?=.{8,64})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*.]).*$",
            message = "Şifre en az 8 karakter, en fazla 64 karakter, 1 Büyük harf 1 " +
                    "Küçük harf, 1 Rakam ve 1 Özel karakter olmalıdır.")
    String password;
    @NotBlank(message = "Lütfen şifrenizi tekrar giriniz.")
    String rePassword;

    @NotNull(message = "Cinsiyet boş olamaz.")
    private Egender gender;

    @NotNull(message = "Rol boş olamaz.")
    private EUserRole userRole; // Sadece EMPLOYEE beklenmeli (validasyon opsiyonel)

    // Employee bilgileri
    @NotBlank(message = "TC kimlik numarası boş olamaz.")
    @Size(min = 11, max = 11, message = "TC kimlik numarası 11 haneli olmalıdır.")
    private String identityNo;

    @NotNull(message = "Doğum tarihi boş olamaz.")
    @Past(message = "Doğum tarihi geçmiş bir tarih olmalıdır.")
    private LocalDate birthDate;

    private String address;

    @NotNull(message = "Pozisyon boş olamaz.")
    private EPosition position;

    @NotNull(message = "İşe giriş tarihi boş olamaz.")
    private LocalDate dateOfEmployment;

    private LocalDate dateOfTermination;

    @NotNull(message = "Maaş boş olamaz.")
    private Double salary;

    @NotNull(message = "Çalışma durumu boş olamaz.")
    private EEmploymentStatus employmentStatus;

    @NotBlank(message = "SGK numarası boş olamaz.")
    private String socialSecurityNumber;
}