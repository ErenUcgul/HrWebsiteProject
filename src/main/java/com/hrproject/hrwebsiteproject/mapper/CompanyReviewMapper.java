package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.CompanyReviewCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyReviewResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Company;
import com.hrproject.hrwebsiteproject.model.entity.CompanyReview;
import com.hrproject.hrwebsiteproject.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyReviewMapper {

    CompanyReview toEntity(CompanyReviewCreateRequestDto dto);

    @Mapping(source = "company.companyName", target = "companyName")
    @Mapping(expression = "java(user.getFirstName() + \" \" + user.getLastName())", target = "managerFullName")

    @Mapping(source = "review.content", target = "reviewContent")
    @Mapping(source = "review.reviewStatus", target = "reviewStatus")
    @Mapping(source = "review.rejectionReason", target = "rejectionReason")
    @Mapping(source ="review.avatar",target = "avatar")
    CompanyReviewResponseDto toDto(CompanyReview review, Company company, User user);
}
