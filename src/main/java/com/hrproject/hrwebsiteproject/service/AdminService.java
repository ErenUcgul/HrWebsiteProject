package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.WelcomeMailRequestDto;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.util.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final CompanyService companyService;
    private final MailSenderService mailSenderService;

    public void approveUserAndCompany(Long userId) {
        // Kullanıcı bulunur
        User user = userService.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // Kullanıcının durumu kontrol edilir,ACTIVE ise onaylanamaz.
        if (user.getState() == EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_ALREADY_ACTIVE);
        }
        // Kullanıcının durumu kontrol edilir,REJECTED ve INREVIEW ise onaylanabilir.
        if (user.getState() == EUserState.PENDING) {
            throw new HrWebsiteProjectException(ErrorType.USER_STATE_IS_PENDING);
        }

        // Kullanıcının şirketi bulunur
        Company company = companyService.findByUserId(user.getId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        // Şirketin durumu kontrol edilir,REJECTED ve INREVIEW ise onaylanabilir
        if (company.getState() == ECompanyState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_ALREADY_ACTIVE);
        }
        if (company.getState() == ECompanyState.PENDING) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_STATE_IS_PENDING);
        }

        // Hem kullanıcı hem şirket aktif yapılır
        user.setState(EUserState.ACTIVE);
        company.setState(ECompanyState.ACTIVE);

        userService.save(user);
        companyService.save(company);

        String subject = "HrWebsiteProject'e Hoşgeldiniz!";
        String message = String.format(
                "Merhaba %s %s,\n\n" +
                        "HrWebsiteProject ailesine katıldığınız için teşekkür ederiz! Hesabınız ve şirketiniz başarılı bir şekilde aktif edilmiştir.\n" +
                        "Artık tüm özelliklerimizden faydalanabilirsiniz.\n\n" +
                        "Başarılar dileriz,\n" +
                        "HrWebsiteProject Ekibi (Başak \uD83C\uDF1F ,Anıl \uD83D\uDC27 ,Mert \uD83E\uDD81 ,Eren \uD83D\uDE4A)",
                user.getFirstName(),
                user.getLastName()
        );
        //TODO:Membership bilgisi de verilebilir.
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
}
