package com.hrproject.hrwebsiteproject.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class HrWebsiteProjectSecurityConfig {
    @Bean
    public JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("securityFilterChain Devrede!!!");
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        //TODO: projeye göre güncellenecek kullanıcıya izin verilen adresler
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/v1/dev/auth/register",
                                "v1/dev/auth/verify-email", "v1/dev/admin/approve-user-and-company", "v1/dev/auth/login",
                                "v1/dev/auth/forgot-password", "v1/dev/auth/reset-password",
                                "v1/dev/user/profile", "v1/dev/user/change-email", "v1/dev/user/change-password", "/v1/dev/user/deactivate-account",
                                "/v1/dev/publicapi/homepage-content",
                                "/v1/dev/publicapi/how-it-works",
                                "/v1/dev/publicapi/platform-features",
                                "/v1/dev/publicapi/holidays",
                                "/v1/dev/admin/update-content",
                                "v1/dev/auth/refresh-access-token",
                                "v1/dev/admin/dashboard",
                                "v1/dev/company/dashboard","v1/dev/employee/dashboard").permitAll()

                        .requestMatchers("/admin/**", "/v1/dev/post/show-all-post", "v1/dev/auth/verify-email",
                                "v1/dev/admin/approve-user-and-company", "v1/dev/auth/login", "v1/dev/auth/forgot-password",
                                "v1/dev/auth/reset-password").hasAuthority("ADMIN")

                        .requestMatchers("/manager/**", "/v1/dev/post/show-all-post", "v1/dev/auth/verify-email", "v1/dev/auth/login",
                                "v1/dev/auth/forgot-password", "v1/dev/auth/reset-password").hasAuthority("MANAGER")
                        .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}