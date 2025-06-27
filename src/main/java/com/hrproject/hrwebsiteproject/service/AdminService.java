package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.WelcomeMailRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.LoginResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.entity.RefreshToken;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.repository.UserRepository;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import com.hrproject.hrwebsiteproject.util.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final CompanyService companyService;
    private final MailSenderService mailSenderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;
    private final RefreshTokenService refreshTokenService;

    public void approveUserAndCompany(Long userId, Long performedByUserId) {
        // ROL KONTROLÜ
        User performer = userService.findById(performedByUserId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        if (performer.getUserRole() != EUserRole.ADMIN && performer.getUserRole() != EUserRole.SUPER_ADMIN) {
            throw new HrWebsiteProjectException(ErrorType.UNAUTHORIZED_REQUEST);
        }

        // Kullanıcı bulunur
        User user = userService.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // Kullanıcı durumu kontrol edilir
        if (user.getState() == EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_ALREADY_ACTIVE);
        }
        if (user.getState() == EUserState.PENDING) {
            throw new HrWebsiteProjectException(ErrorType.USER_STATE_IS_PENDING);
        }

        // Şirket bulunur
        Company company = companyService.findByUserId(user.getId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        // Şirket durumu kontrol edilir
        if (company.getState() == ECompanyState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_ALREADY_ACTIVE);
        }
        if (company.getState() == ECompanyState.PENDING) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_STATE_IS_PENDING);
        }

        // Onayla
        user.setState(EUserState.ACTIVE);
        company.setState(ECompanyState.ACTIVE);

        userService.save(user);
        companyService.save(company);

        // Hoşgeldin maili gönder
        String subject = "HrWebsiteProject'e Hoşgeldiniz!";
        String message = String.format(
                "Merhaba %s %s,\n\n" +
                        "HrWebsiteProject ailesine katıldığınız için teşekkür ederiz! Hesabınız ve şirketiniz başarılı bir şekilde aktif edilmiştir.\n" +
                        "Artık tüm özelliklerimizden faydalanabilirsiniz.\n\n" +
                        "Başarılar dileriz,\n" +
                        "HrWebsiteProject Ekibi (Başak 🌟 ,Anıl 🐧 ,Mert 🦁 ,Eren 🙊)",
                user.getFirstName(),
                user.getLastName()
        );

        mailSenderService.sendInformationMail(new WelcomeMailRequestDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                subject,
                message
        ));
    }

    public void rejectCompanyById(Long companyId) {
        // Şirket bulunur
        Company company = companyService.findByCompanyId(companyId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        // Zaten reddedilmiş mi?
        if (company.getState() == ECompanyState.REJECTED) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_ALREADY_REJECTED);
        }
        // Aktif edilen şirket reddedilemez
        if (company.getState() == ECompanyState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_ALREADY_APPROVED);
        }

        // Şirket RED durumuna alınır
        company.setState(ECompanyState.REJECTED);
        companyService.save(company);

        // Kullanıcıya bilgi maili gönder
        User user = userService.findById(company.getUserId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        String subject = "HrWebsiteProject Şirket Başvurusu Reddedildi";
        String message = String.format(
                "Merhaba %s %s,\n\n" +
                        "Yapmış olduğunuz şirket başvurusu değerlendirilmiş ve uygun bulunmamıştır.\n" +
                        "Lütfen kayıt için yüklediğiniz evrak ve bilgileri kontrol ediniz. \n\n" +
                        "HrWebsiteProject Ekibi (Başak 🌟, Anıl 🐧, Mert 🦁, Eren 🙊)",
                user.getFirstName(),
                user.getLastName()
        );

        mailSenderService.sendInformationMail(new WelcomeMailRequestDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                subject,
                message
        ));
    }

    public LoginResponseDto adminLogin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // Rol kontrolü: sadece ADMIN olabilir
        if (user.getUserRole() != EUserRole.ADMIN && user.getUserRole() != EUserRole.SUPER_ADMIN) {
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
