package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeLeaveRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeLeaveResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeLeave;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeLeaveMapper {

    EmployeeLeaveMapper INSTANCE = Mappers.getMapper(EmployeeLeaveMapper.class);



    @Mappings({
            @Mapping(source = "companyLeaveTypeId", target = "companyLeaveTypeId"),
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "reason", target = "reason"),

            @Mapping(target = "totalDays", ignore = true),
            @Mapping(target = "leaveStatus", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "companyId", ignore = true)
    })
    EmployeeLeave toEntity(EmployeeLeaveRequestDto dto);

    @Mappings({
            @Mapping(source = "employeeLeave.id", target = "id"),
            @Mapping(source = "leaveType.name", target = "leaveTypeName"),
            @Mapping(source = "leaveType.description", target = "leaveTypeDescription"),
            @Mapping(source = "employeeLeave.startDate", target = "startDate"),
            @Mapping(source = "employeeLeave.endDate", target = "endDate"),
            @Mapping(source = "employeeLeave.totalDays", target = "totalDays"),
            @Mapping(source = "employeeLeave.leaveStatus", target = "leaveStatus"),
            @Mapping(source = "employeeLeave.reason", target = "reason")
    })
    EmployeeLeaveResponseDto toResponseDto(EmployeeLeave employeeLeave, LeaveType leaveType);
}

