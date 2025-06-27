package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.ActivationRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LoginRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.RegisterRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ResetPasswordRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.dto.response.LoginResponseDto;
import com.hrproject.hrwebsiteproject.service.AdminService;
import com.hrproject.hrwebsiteproject.service.EmployeeAuthService;
import com.hrproject.hrwebsiteproject.service.RegistrationService;
import com.hrproject.hrwebsiteproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final EmployeeAuthService employeeAuthService;
    private final AdminService adminService;

    @PostMapping(EndPoints.REGISTER)
    public ResponseEntity<BaseResponse<Boolean>> registerWithCompany(@RequestBody @Valid RegisterRequestDto dto) {
        if (!dto.rePassword().equals(dto.password())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_MISMATCH_ERROR);
        }
        registrationService.registerWithCompany(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .success(true)
                .message("Kayıt Başarılı").build());
    }

    @PostMapping(EndPoints.VERIFY_EMAIL)
    public ResponseEntity<BaseResponse<Boolean>> activateAccount(@RequestBody @Valid ActivationRequestDto dto) {
        registrationService.verifyEmail(dto.email(), dto.code());

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .data(true)
                .success(true)
                .message("Mailiniz doğrulandı.")
                .build());
    }

    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<BaseResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto tokens = registrationService.login(dto.email(), dto.password());
        return ResponseEntity.ok(BaseResponse.<LoginResponseDto>builder()
                .code(200)
                .data(tokens)
                .success(true)
                .message("Giriş başarılı.")
                .build());
    }


    @GetMapping(EndPoints.FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        registrationService.forgotPassword(email);
        return ResponseEntity.ok("Şifrenizi onaylamak için reset-password alanına gidiniz.");
    }

    @PostMapping(EndPoints.RESET_PASSWORD)
    public ResponseEntity<BaseResponse<Boolean>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
        registrationService.resetPassword(dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .data(true)
                .message("Şifre başarıyla güncellendi.")
                .build());
    }

    @PostMapping(EndPoints.LOGOUT)
    public ResponseEntity<BaseResponse<Boolean>> logout() {

        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Başarıyla çıkış yapıldı.")
                .data(true)
                .build());
    }

    @PostMapping(EndPoints.REFRESH_ACCESS_TOKEN)
    public ResponseEntity<BaseResponse<String>> refreshAccessToken(@RequestParam String refreshToken) {
        String newAccessToken = registrationService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .data(newAccessToken)
                .message("Yeni access token üretildi.")
                .build());
    }

    @PostMapping(EndPoints.EMPLOYEE_LOGIN)
    public ResponseEntity<BaseResponse<LoginResponseDto>> employeeLogin(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto tokens = employeeAuthService.employeeLogin(dto.email(), dto.password());

        return ResponseEntity.ok(BaseResponse.<LoginResponseDto>builder()
                .code(200)
                .data(tokens)
                .success(true)
                .message("Personel girişi başarılı.")
                .build());
    }

    @PostMapping(EndPoints.ADMIN_LOGIN)
    public ResponseEntity<BaseResponse<LoginResponseDto>> adminLogin(@RequestBody @Valid LoginRequestDto dto) {
        LoginResponseDto tokens = adminService.adminLogin(dto.email(), dto.password());

        return ResponseEntity.ok(BaseResponse.<LoginResponseDto>builder()
                .code(200)
                .data(tokens)
                .success(true)
                .message("Admin girişi başarılı.")
                .build());
    }

}
