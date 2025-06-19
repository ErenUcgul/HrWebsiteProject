package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.EmployeeMapper;
import com.hrproject.hrwebsiteproject.mapper.UserMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeDetailDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeListDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import com.hrproject.hrwebsiteproject.model.enums.EUserRole;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import com.hrproject.hrwebsiteproject.model.enums.Egender;
import com.hrproject.hrwebsiteproject.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public void createEmployee(EmployeeCreateRequestDto dto, Long companyId) {
        // 1. TC Kimlik numarası sistemde var mı?
        if (employeeRepository.existsByIdentityNo(dto.getIdentityNo())) {
            throw new HrWebsiteProjectException(ErrorType.IDENTITY_NO_ALREADY_EXISTS);
        }

        // 2. SGK numarası daha önce girilmiş mi?
        if (employeeRepository.existsBySocialSecurityNumber(dto.getSocialSecurityNumber())) {
            throw new HrWebsiteProjectException(ErrorType.SOCIAL_SECURITY_ALREADY_EXISTS);
        }

        // 3. Kullanıcı daha önce aynı telefonla kayıtlı mı?
        if (userService.existsByPhone(dto.getPhone())) {
            throw new HrWebsiteProjectException(ErrorType.PHONE_ALREADY_EXISTS);
        }

        // 4. Doğum tarihi 15 yaşından küçük mü?
        LocalDate fifteenYearsAgo = LocalDate.now().minusYears(15);
        if (dto.getBirthDate().isAfter(fifteenYearsAgo)) {
            throw new HrWebsiteProjectException(ErrorType.AGE_LESS_THAN_15);
        }

        // 5. Sadece EMPLOYEE rolüne izin veriyoruz
        if (dto.getUserRole() != EUserRole.EMPLOYEE) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_USER_ROLE);
        }
        // 6. Kullanıcı daha önce aynı mail adresiyle kayıtlı mı?
        if (userService.existsByMail(dto.getEmail())) {
            throw new HrWebsiteProjectException(ErrorType.EMAIL_ALREADY_TAKEN);
        }

        // 7. User oluştur
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .avatar(dto.getAvatar())
                .gender(dto.getGender())
                .userRole(dto.getUserRole())
                .state(EUserState.ACTIVE)
                .build();
        user = userService.save(user);

        // 8. Employee oluştur
        Employee employee = Employee.builder()
                .userId(user.getId())
                .identityNo(dto.getIdentityNo())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .position(dto.getPosition())
                .dateOfEmployment(dto.getDateOfEmployment())
                .dateOfTermination(dto.getDateOfTermination())
                .salary(dto.getSalary())
                .employmentStatus(dto.getEmploymentStatus())
                .socialSecurityNumber(dto.getSocialSecurityNumber())
                .companyId(companyId)
                .build();

        employeeRepository.save(employee);
    }

    public void updateEmployee(Long employeeId, Long companyId, EmployeeUpdateRequestDto dto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EMPLOYEE_NOT_FOUND));

        if (!employee.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        User user = userService.findById(employee.getUserId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        // DTO içindeki gender ve role string, enum'a parse etmemiz gerek
        // Bu değerleri User nesnesine setlemeden önce geçici olarak user nesnesine manuel ekleyeceğiz
        // sonra MapStruct ile kalan alanları aktaracağız

        if (dto.gender() != null) {
            user.setGender(Egender.valueOf(dto.gender()));
        }
        if (dto.userRole() != null) {
            user.setUserRole(EUserRole.valueOf(dto.userRole()));
        }

        // MapStruct ile kalan alanları güncelle
        userMapper.updateUserFromEmployeeDto(dto, user);

        userService.save(user);

        employeeMapper.updateEmployeeFromDto(dto, employee);
        employeeRepository.save(employee);
    }
    //Soft delete yapıyoruz,veritabanında data silinmemesi için
    public void softDeleteEmployee(Long employeeId, Long companyId) {
        Employee employee = getEmployeeByIdAndCompany(employeeId, companyId);
        employee.setEmploymentStatus(EEmploymentStatus.DELETED);
        employeeRepository.save(employee);
    }

    public void activateEmployee(Long employeeId, Long companyId) {
        Employee employee = getEmployeeByIdAndCompany(employeeId, companyId);
        employee.setEmploymentStatus(EEmploymentStatus.ACTIVE);
        employeeRepository.save(employee);
    }

    public void deactivateEmployee(Long employeeId, Long companyId) {
        Employee employee = getEmployeeByIdAndCompany(employeeId, companyId);
        employee.setEmploymentStatus(EEmploymentStatus.INACTIVE);
        employeeRepository.save(employee);
    }

    //17.06
    public List<EmployeeListDto> getAllEmployees(Long companyId) {
        List<Employee> employees = employeeRepository.findAllByCompanyId(companyId);
        return employees.stream()
                .map(employee -> {
                    User user = userService.findById(employee.getUserId())
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
                    return employeeMapper.toListDto(employee, user);
                })
                .toList();
    }
    public EmployeeDetailDto getEmployeeDetails(Long employeeId, Long companyId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EMPLOYEE_NOT_FOUND));

        if (!employee.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        User user = userService.findById(employee.getUserId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));

        return employeeMapper.toDetailDto(employee, user);
    }
    // Ortak metod: Hem güvenlik hem tekrar kontrol için
    private Employee getEmployeeByIdAndCompany(Long employeeId, Long companyId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EMPLOYEE_NOT_FOUND));

        if (!employee.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        return employee;
    }

    public Employee findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EMPLOYEE_NOT_FOUND));
    }

    public Long getEmployeeIdByUserId(Long userId) {
        return employeeRepository.findByUserId(userId)
                .map(Employee::getId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.EMPLOYEE_NOT_FOUND));
    }

}
