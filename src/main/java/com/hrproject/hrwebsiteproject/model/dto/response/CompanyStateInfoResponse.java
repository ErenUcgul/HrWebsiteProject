package com.hrproject.hrwebsiteproject.model.dto.response;

import com.hrproject.hrwebsiteproject.model.enums.ECompanyState;
import lombok.Builder;

@Builder
public record CompanyStateInfoResponse(
        String companyName,
        ECompanyState state
) {
}