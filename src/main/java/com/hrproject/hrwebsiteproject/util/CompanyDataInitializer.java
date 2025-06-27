package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.dto.request.RegisterRequestDto;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.*;
import com.hrproject.hrwebsiteproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyDataInitializer {

    private final RegistrationService registrationService;
    private final CompanyService companyService;
    private final UserService userService;



    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        RegisterRequestDto company1 = new RegisterRequestDto(
                "Ahmet",
                "Yılmaz",
                "Password123!",
                "Password123!",
                "eren.ucgul@bilgeadamboost.com",
                "05551234567",
                null,
                Egender.MALE,
                "BilgeAdamB",
                "İstanbul, Türkiye",
                ERegion.TURKEY,
                "02123334455",
                "info@bilgeadam.com",
                null,
                ECompanyType.TECHNOLOGY
        );

        RegisterRequestDto company2 = new RegisterRequestDto(
                "Ayşe",
                "Kara",
                "StrongPass1@",
                "StrongPass1@",
                "basak.ozek@bilgeadamboost.com",
                "05557654321",
                null,
                Egender.FEMALE,
                "BilgeAdamBo",
                "Ankara, Türkiye",
                ERegion.TURKEY,
                "03121234567",
                "contact@healthcareplus.com",
                null,
                ECompanyType.HEALTHCARE
        );

//        RegisterRequestDto company3 = new RegisterRequestDto(
//                "Mehmet",
//                "Demir",
//                "SecurePass9#",
//                "SecurePass9#",
//                "mert.sari@bilgeadamboost.com",
//                "05559876543",
//                null,
//                Egender.MALE,
//                "BilgeAdamBoo",
//                "İzmir, Türkiye",
//                ERegion.TURKEY,
//                "02321239876",
//                "info@constructpro.com",
//                null,
//                ECompanyType.CONSTRUCTION
//        );

        registerAndApprove(company1);
        registerAndApprove(company2);
//        registerAndApprove(company3);

    }

    private void registerAndApprove(RegisterRequestDto dto) {
        try {
            registrationService.registerWithCompany(dto);
            System.out.println("Registered: " + dto.email());

            Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(dto.email()));
            userOptional.ifPresent(user -> {
                try {
                    // Kullanıcı ve şirket doğrudan burada aktif ediliyor
                    user.setState(EUserState.ACTIVE);
                    userService.save(user);

                    companyService.findByUserId(user.getId()).ifPresent(company -> {
                        company.setState(ECompanyState.ACTIVE);
                        companyService.save(company);
                        System.out.println("Company also activated manually for: " + dto.email());
                    });

                    System.out.println("Manually approved without adminService: " + dto.email());

                } catch (Exception e) {
                    System.err.println("Manual approval failed for: " + dto.email() + " -> " + e.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("Registration failed for " + dto.email() + ": " + e.getMessage());
        }
    }
}
