package com.hrproject.hrwebsiteproject.model.dto.response;

public class UserTokenInfo {
    private final Long userId;
    private final String role;

    public UserTokenInfo(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
