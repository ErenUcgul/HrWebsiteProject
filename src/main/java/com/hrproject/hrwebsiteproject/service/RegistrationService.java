package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.mapper.CompanyMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.MailSenderRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ResetPasswordRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.LoginResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.RefreshToken;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.repository.RefreshTokenRepository;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.UserMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.RegisterRequestDto;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.util.CodeGenerator;
import com.hrproject.hrwebsiteproject.util.MailSenderService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CompanyService companyService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final CodeGenerator codeGenerator;
    private final JwtManager jwtManager;
    private final RefreshTokenService refreshTokenService;

    /**
     * Kullanıcı ve şirket kaydını birlikte yapar.
     */
    @Transactional
    public void registerWithCompany(RegisterRequestDto dto) {
        // 1. Şirket var mı kontrol et
        Optional<Company> existingCompany = companyService.findByCompanyName(dto.companyName());
        if (existingCompany.isPresent()) {
            throw new HrWebsiteProjectException(ErrorType.ALREADY_EXIST_COMPANY);
        }

        // 2. Email domain kontrolü
        if (!isEmailDomainValidForCompany(dto.email(), dto.companyName())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_COMPANY_EMAIL_DOMAIN);
        }

        // 3. Şirket oluştur
        Company company = CompanyMapper.INSTANCE.toEntity(dto);
        company.setState(ECompanyState.PENDING);
        companyService.save(company);

        // 4. Kullanıcı oluştur
        User user = UserMapper.INSTANCE.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setState(EUserState.PENDING);
        user.setActivationCode(codeGenerator.generateActivationCode());
        user.setUserRole(EUserRole.MANAGER);

        user = userService.save(user);
        company.setUserId(user.getId());

        // 5. Aktivasyon maili gönder
        mailSenderService.sendMail(new MailSenderRequestDto(user.getEmail(), user.getActivationCode()));
    }

    // Email domain şirket adı ile uyumlu mu kontrol eden yardımcı metod
    private boolean isEmailDomainValidForCompany(String email, String companyName) {
        if (email == null || companyName == null) return false;

        companyName = companyName.toLowerCase()
                .replaceAll("\\s", "")   // boşlukları sil
                .replace("ç", "c") //Şirket adındaki olası Türkçe karakterleri İngilizceleştirme
                .replace("ğ", "g")
                .replace("ı", "i")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ü", "u");
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        // Örneğin: domain bilgeadam.com, bilgeadamboost.com olabilir
        // domain içinde companyName geçmeli
        return domain.contains(companyName);
    }
//company admin
    public void verifyEmail(String email, String code) {
        User user = userService.findByEmail(email);


        if (user.getState() == EUserState.IN_REVIEW) {
            throw new HrWebsiteProjectException(ErrorType.USER_ALREADY_IN_REVIEW);
        }

        if (!code.equals(user.getActivationCode())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_ACTIVATION_CODE);
        }

        user.setState(EUserState.IN_REVIEW);
        user.setActivationCode(null);

        userService.save(user);
        Company company = companyService.findByUserId(user.getId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        company.setState(ECompanyState.IN_REVIEW);
        companyService.save(company);
    }

//    public String login(String email, String password) {
//        User user = userService.findByEmail(email);
//
//
//        // Şifre kontrolü
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new HrWebsiteProjectException(ErrorType.PASSWORD_MISMATCH_ERROR);
//        }
//
//        // Kullanıcı durumu kontrolü
//        if (user.getState() != EUserState.ACTIVE) {
//            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
//        }
//
//        // Şirket durumu kontrolü
//        Company company = companyService.findByUserId(user.getId())
//                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
//
//        if (company.getState() != ECompanyState.ACTIVE) {
//            throw new HrWebsiteProjectException(ErrorType.COMPANY_NOT_ACTIVE);
//        }
//
//        // Token oluşturulup döndürülür
//        return jwtManager.generateToken(user.getId(), user.getUserRole());
//    }

    public LoginResponseDto login(String email, String password) {
        User user = userService.findByEmail(email);

        // Şifre kontrolü
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_MISMATCH_ERROR);
        }

        // Kullanıcı durumu kontrolü
        if (user.getState() != EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
        }

        // Şirket durumu kontrolü
        Company company = companyService.findByUserId(user.getId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        if (company.getState() != ECompanyState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_NOT_ACTIVE);
        }

        // Access Token üret
        String accessToken = jwtManager.generateAccessToken(user.getId(), user.getUserRole());

        // Refresh Token oluştur ve kaydet (RefreshToken entity üzerinden)
        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponseDto(accessToken, refreshTokenEntity.getToken());
    }

    public void forgotPassword(String email) {
        User user = userService.findByEmail(email);

        // 1. Kullanıcının hesabı aktif değilse işlem yapılmaz
        if (user.getState() != EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_NOT_ACTIVE);
        }

        // 2. Daha önce kod gönderildiyse tekrar gönderilmesini engelle
        //TODO:CODE zamanı kısaltılıp denenecek,sonuca göre kontrol edilecek
        if (user.getPasswordResetCode() != null) {
            throw new HrWebsiteProjectException(ErrorType.RESET_CODE_ALREADY_SENT);
        }

        // 3. Kod üretimi ve kaydetme
        String resetCode = CodeGenerator.generateResetPasswordCode();
        user.setPasswordResetCode(resetCode);
        userService.save(user);

        // 4. Mail gönderimi
        MailSenderRequestDto mailModel = new MailSenderRequestDto(email, resetCode);
        mailSenderService.sendPasswordMail(mailModel);
    }

    public void resetPassword(ResetPasswordRequestDto dto) {

        if (!dto.newPassword().equals(dto.rePassword())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_MISMATCH_ERROR);
        }

        User user = userService.findByEmail(dto.email());

        // Eski şifreyle yeni şifre aynı mı kontrol et
        if (passwordEncoder.matches(dto.newPassword(), user.getPassword())) {
            throw new HrWebsiteProjectException(ErrorType.PASSWORD_SAME_AS_OLD);
        }

        if (!dto.resetCode().equals(user.getPasswordResetCode())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_RESET_CODE);
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        user.setPasswordResetCode(null); // Kod kullanıldıktan sonra temizlenir
        userService.save(user);
    }

    public String refreshAccessToken(String refreshToken) {
        RefreshToken tokenEntity = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.INVALID_REFRESH_TOKEN));

        if (refreshTokenService.isExpired(tokenEntity)) {
            throw new HrWebsiteProjectException(ErrorType.EXPIRED_REFRESH_TOKEN);
        }

        User user = userService.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // Yeni access token üret
        return jwtManager.generateAccessToken(user.getId(), user.getUserRole());
    }
}

