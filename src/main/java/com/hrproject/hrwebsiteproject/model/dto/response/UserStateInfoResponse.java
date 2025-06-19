package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import lombok.Builder;

@Builder
public record UserStateInfoResponse(
        Long id,
        String fullName,
        EUserState state
) {
}
