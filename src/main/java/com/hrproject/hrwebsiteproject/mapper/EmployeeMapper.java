package com.hrproject.hrwebsiteproject.mapper;

import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeDetailDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeListDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeCreateRequestDto dto);

    EmployeeResponseDto toResponseDto(Employee employee);

    void updateEmployeeFromDto(EmployeeUpdateRequestDto dto, @MappingTarget Employee employee);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "employee.identityNo", target = "identityNo")
    @Mapping(source = "employee.position", target = "position")
    @Mapping(source = "employee.employmentStatus", target = "employmentStatus")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    EmployeeListDto toListDto(Employee employee, User user);

    // Detay DTO'su (Employee + User)
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "employee.identityNo", target = "identityNo")
    @Mapping(source = "employee.birthDate", target = "birthDate")
    @Mapping(source = "employee.address", target = "address")
    @Mapping(source = "employee.position", target = "position")
    @Mapping(source = "employee.dateOfEmployment", target = "dateOfEmployment")
    @Mapping(source = "employee.dateOfTermination", target = "dateOfTermination")
    @Mapping(source = "employee.salary", target = "salary")
    @Mapping(source = "employee.employmentStatus", target = "employmentStatus")
    @Mapping(source = "employee.socialSecurityNumber", target = "socialSecurityNumber")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.avatar", target = "avatar")
    @Mapping(source = "user.gender", target = "gender")
    @Mapping(source = "user.state", target = "state")
    EmployeeDetailDto toDetailDto(Employee employee, User user);
}
