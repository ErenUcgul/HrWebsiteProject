package com.hrproject.hrwebsiteproject.model.dto.request;

public record WelcomeMailRequestDto(
        String email,
        String firstName,
        String lastName,
        String subject,
        String body
) {
}
