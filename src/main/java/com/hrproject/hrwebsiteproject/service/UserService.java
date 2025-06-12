package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.UserMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.*;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.repository.UserRepository;
import com.hrproject.hrwebsiteproject.util.CodeGenerator;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import com.hrproject.hrwebsiteproject.util.MailSenderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtManager jwtManager;
    private final CodeGenerator codeGenerator;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void register(RegisterRequestDto dto) {

        if (userRepository.existsByEmail(dto.email()))
            throw new HrWebsiteProjectException(ErrorType.EMAIL_ALREADY_TAKEN);

        User user = UserMapper.INSTANCE.toEntity(dto);

        //  Şifreyi BCrypt ile hash'leme işlemi
        user.setPassword(passwordEncoder.encode(dto.password()));

        user.setActivationCode(codeGenerator.generateActivationCode());
        user.setState(EUserState.PENDING);

        userRepository.save(user);

        mailSenderService.sendMail(
                new MailSenderRequestDto(user.getEmail(), user.getActivationCode())
        );
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
    }

    public User getProfile(String token) {
        Optional<Long> userId = jwtManager.validateToken(token);
        if (userId.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_TOKEN);
        }
        Optional<User> optionalUser = userRepository.findById(userId.get());
        if (optionalUser.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND);
        }
        return optionalUser.get();
    }

    public User updateProfile(String token, UpdateProfileRequestDto dto) {
        Optional<Long> userId = jwtManager.validateToken(token);
        if (userId.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_TOKEN);
        }

        User user = userRepository.findById(userId.get())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        if (user.getState() != EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
        }

        userMapper.updateUserFromDto(dto, user);
        return userRepository.save(user);
    }
    @Transactional
    public void changeEmail(String token, ChangeEmailRequestDto dto) {
        User user = getActiveUserFromToken(token);

        if (user.getEmail().equals(dto.newEmail())) {
            throw new HrWebsiteProjectException(ErrorType.EMAIL_SAME_AS_OLD);
        }

        if (userRepository.existsByEmail(dto.newEmail())) {
            throw new HrWebsiteProjectException(ErrorType.EMAIL_ALREADY_TAKEN);
        }

        String oldDomain = extractDomain(user.getEmail());
        String newDomain = extractDomain(dto.newEmail());

        if (!oldDomain.contains(newDomain) && !newDomain.contains(oldDomain)) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_COMPANY_EMAIL_DOMAIN);
        }

        String code = codeGenerator.generateActivationCode();
        user.setPendingEmail(dto.newEmail());
        user.setEmailChangeCode(code);
        userRepository.save(user);

        mailSenderService.sendMail(new MailSenderRequestDto(dto.newEmail(), code));
    }

    @Transactional
    public void confirmEmailChange(String token, ConfirmEmailChangeRequestDto dto) {
        User user = getActiveUserFromToken(token);

        if (user.getPendingEmail() == null || user.getEmailChangeCode() == null) {
            throw new HrWebsiteProjectException(ErrorType.NO_PENDING_EMAIL_CHANGE);
        }

        if (!dto.verificationCode().equals(user.getEmailChangeCode())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_ACTIVATION_CODE);
        }

        user.setEmail(user.getPendingEmail());
        user.setPendingEmail(null);
        user.setEmailChangeCode(null);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String token, ChangePasswordRequestDto dto) {
        User user = getActiveUserFromToken(token);

        // 1. Eski şifre doğru mu?
        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_OLD_PASSWORD);
        }

        // 2. Yeni şifre eski şifreyle aynı mı?
        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_SAME_AS_OLD);
        }

        // 3. Yeni şifre ve tekrar uyuşuyor mu?
        if (!dto.newPassword().equals(dto.reNewPassword())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_MISMATCH_ERROR);
        }

        // 4. Yeni şifreyi kaydet
        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deactivateAccount(String token) {
        User user = getActiveUserFromToken(token);

        // İlk admin hesabı mı kontrolü
        if (user.getUserRole() == EUserRole.SUPER_ADMIN) {
            throw new HrWebsiteProjectException(ErrorType.CANNOT_DEACTIVATE_SUPER_ADMIN);
        }

        // Hesabı pasife al
        user.setState(EUserState.INACTIVE);
        userRepository.save(user);
    }

    public User getActiveUserFromToken(String token) {
        Long userId = jwtManager.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        if (user.getState() != EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
        }
        return user;
    }

    private String extractDomain(String email) {
        if (email == null || !email.contains("@")) {
            return "";
        }
        return email.substring(email.indexOf("@") + 1).toLowerCase();
    }

    public int countAll() {
        return (int) userRepository.count();
    }
}
