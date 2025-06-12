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

        // Kullanıcının durumu kontrol edilir,sadece ACTIVE ise onaylanabilir.
        if (user.getState() == EUserState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.USER_ALREADY_ACTIVE);
        }

        // Kullanıcının şirketi bulunur
        Company company = companyService.findByUserId(user.getId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));

        // Şirketin durumu kontrol edilir
        if (company.getState() == ECompanyState.ACTIVE) {
            throw new HrWebsiteProjectException(ErrorType.COMPANY_ALREADY_ACTIVE);
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
        mailSenderService.sendWelcomeMail(new WelcomeMailRequestDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                subject,
                message
        ));
    }
}
