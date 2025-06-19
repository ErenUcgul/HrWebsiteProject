package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.LeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LeaveTypeMapper {
    LeaveTypeMapper INSTANCE = Mappers.getMapper(LeaveTypeMapper.class);

    LeaveType toEntity(LeaveTypeCreateRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(LeaveTypeUpdateRequestDto dto, @MappingTarget LeaveType leaveType);

    LeaveTypeResponseDto toResponseDto(LeaveType leaveType);
}