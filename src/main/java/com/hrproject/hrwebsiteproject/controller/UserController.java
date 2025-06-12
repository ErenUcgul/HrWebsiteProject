package com.hrproject.hrwebsiteproject.controller;

import com.hrproject.hrwebsiteproject.constant.EndPoints;
import com.hrproject.hrwebsiteproject.model.dto.request.ChangeEmailRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ChangePasswordRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ConfirmEmailChangeRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.UpdateProfileRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.BaseResponse;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.service.AdminService;
import com.hrproject.hrwebsiteproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.USER)
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @GetMapping(EndPoints.PROFILE)
    public ResponseEntity<BaseResponse<User>> getProfile(@RequestParam String token) {
        return ResponseEntity.ok(BaseResponse.<User>builder()
                .code(200)
                .data(userService.getProfile(token))
                .success(true)
                .message("Profil bilgileri getirildi.").build());
    }

    @PutMapping(EndPoints.PROFILE)
    public ResponseEntity<BaseResponse<User>> updateProfile(String token,
                                                            @RequestBody @Valid UpdateProfileRequestDto dto) {
        User updated = userService.updateProfile(token, dto);
        return ResponseEntity.ok(BaseResponse.<User>builder()
                .code(200)
                .success(true)
                .message("Profil güncellendi.")
                .data(updated)
                .build());
    }

    @PutMapping(EndPoints.CHANGE_EMAIL)
    public ResponseEntity<BaseResponse<Boolean>> changeEmail(String token,
                                                             @RequestBody @Valid ChangeEmailRequestDto dto) {
        userService.changeEmail(token, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Email değiştirme işlemi başarıyla başlatıldı. Lütfen doğrulama kodunu kontrol edin.")
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.CONFIRM_CHANGE_EMAIL)
    public ResponseEntity<BaseResponse<String>> confirmEmailChange(
            @RequestHeader String token,
            @RequestBody ConfirmEmailChangeRequestDto dto) {
        userService.confirmEmailChange(token, dto);

        return ResponseEntity.ok(BaseResponse.<String>builder()
                .code(200)
                .success(true)
                .message("Email başarıyla değiştirildi.")
                .data("OK")
                .build());
    }

    @PutMapping(EndPoints.CHANGE_PASSWORD)
    public ResponseEntity<BaseResponse<Boolean>> changePassword(@RequestHeader String token,
                                                                @RequestBody @Valid ChangePasswordRequestDto dto) {
        userService.changePassword(token, dto);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Şifre başarıyla değiştirildi.")
                .data(true)
                .build());
    }

    @PutMapping(EndPoints.DEACTIVATE_ACCOUNT)
    public ResponseEntity<BaseResponse<Boolean>> deactivateAccount(@RequestHeader String token) {
        userService.deactivateAccount(token);
        return ResponseEntity.ok(BaseResponse.<Boolean>builder()
                .code(200)
                .success(true)
                .message("Hesap başarıyla devre dışı bırakıldı.")
                .data(true)
                .build());
    }

}
