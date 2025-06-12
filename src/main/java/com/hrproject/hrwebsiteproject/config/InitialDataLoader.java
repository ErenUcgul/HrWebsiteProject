package com.hrproject.hrwebsiteproject.config;

import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitialDataLoader {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${hrwebsite.superadmin.email:admin@hr.com}")
    private String superAdminEmail;

    @Value("${hrwebsite.superadmin.password:Admin1234}")
    private String superAdminPassword;

    @PostConstruct
    public void seedSuperAdmin() {
        boolean exists = userRepository.existsByEmail(superAdminEmail);

        if (!exists) {
            User admin = User.builder()
                    .firstName("Super")
                    .lastName("Admin")
                    .email(superAdminEmail)
                    .password(passwordEncoder.encode(superAdminPassword))
                    .userRole(EUserRole.ADMIN)
                    .state(EUserState.ACTIVE)
                    .build();

            userRepository.save(admin);
            System.out.println("✅ SuperAdmin başarıyla oluşturuldu: " + superAdminEmail);
        } else {
            System.out.println("ℹ️ SuperAdmin zaten mevcut: " + superAdminEmail);
        }
    }
}
