package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.RegisterRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.UpdateProfileRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.RegisterResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RegisterResponseDto toResponseDto(User user);

    User toEntity(RegisterRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateProfileRequestDto dto, @MappingTarget User user);
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromEmployeeDto(EmployeeUpdateRequestDto dto, @MappingTarget User user);
}