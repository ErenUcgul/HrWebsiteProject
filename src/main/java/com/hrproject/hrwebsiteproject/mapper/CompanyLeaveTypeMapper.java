package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.CompanyLeaveTypeRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyLeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.CompanyLeaveType;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyLeaveTypeMapper {
    CompanyLeaveTypeMapper INSTANCE = Mappers.getMapper(CompanyLeaveTypeMapper.class);

    @Mapping(source = "companyLeaveType.id", target = "id")
    @Mapping(source = "leaveType.name", target = "leaveTypeName")
    @Mapping(source = "leaveType.description", target = "leaveTypeDescription")
    @Mapping(source = "companyLeaveType.defaultDayCount", target = "defaultDayCount")
    @Mapping(source = "companyLeaveType.requiresApproval", target = "requiresApproval")
    @Mapping(source = "companyLeaveType.active", target = "active")
    CompanyLeaveTypeResponseDto toResponseDto(CompanyLeaveType companyLeaveType, LeaveType leaveType);

    CompanyLeaveType toEntity(CompanyLeaveTypeRequestDto dto);
}