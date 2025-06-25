package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeExpenseRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeExpenseResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeExpense;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeExpenseMapper {

    EmployeeExpense toEntity(EmployeeExpenseRequestDto dto);

    EmployeeExpenseResponseDto toDto(EmployeeExpense expense);
}
