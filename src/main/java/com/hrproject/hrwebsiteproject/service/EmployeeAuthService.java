package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.response.LoginResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.RefreshToken;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.repository.UserRepository;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager  jwtManager;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDto employeeLogin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // Rol kontrolü: sadece EMPLOYEE olabilir
        if (user.getUserRole() != EUserRole.EMPLOYEE) {
            throw new HrWebsiteProjectException(ErrorType.UNAUTHORIZED_USER_ROLE);
        }

        // Kullanıcı aktif olmalı
        if (user.getState() != EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
        }

        // Şifre kontrolü
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_USERNAME_OR_PASSWORD);
        }

        // JWT Token userId + role içeren access token oluştur
        String accessToken = jwtManager.generateAccessToken(user.getId(), user.getUserRole());

        // Refresh token servisinden alınır
        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponseDto(accessToken, refreshTokenEntity.getToken());
    }
}
