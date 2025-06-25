package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.response.EmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.model.dto.response.PersonalEmbezzlementResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Embezzlement;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EmbezzlementMapper {
    EmbezzlementMapper INSTANCE = Mappers.getMapper(EmbezzlementMapper.class);

    EmbezzlementResponseDto toResponseDto(Embezzlement embezzlement);

    PersonalEmbezzlementResponseDto toPersonalResponseDto(Embezzlement embezzlement);
}