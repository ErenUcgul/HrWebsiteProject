package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.dto.request.MaterialRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.MaterialResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Material;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MaterialMapper {
    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    Material toEntity(MaterialRequestDto dto);

    MaterialResponseDto toResponseDto(Material material);
}
