package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.MaterialRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.MaterialResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Material;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    Material toEntity(MaterialRequestDto dto);

    MaterialResponseDto toResponseDto(Material material);
}
