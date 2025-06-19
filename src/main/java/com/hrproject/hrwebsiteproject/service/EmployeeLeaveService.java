package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.EmployeeLeaveMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.EmployeeLeaveRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveApprovalRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeLeaveResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.CompanyLeaveType;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.EmployeeLeave;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import com.hrproject.hrwebsiteproject.model.enums.ELeaveStatus;
import com.hrproject.hrwebsiteproject.repository.CompanyLeaveTypeRepository;
import com.hrproject.hrwebsiteproject.repository.EmployeeLeaveRepository;
import com.hrproject.hrwebsiteproject.repository.LeaveTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class EmployeeLeaveService {
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeService employeeService;
    private final CompanyLeaveTypeRepository companyLeaveTypeRepository;
    private final LeaveTypeService leaveTypeService;
    private final CompanyLeaveTypeService companyLeaveTypeService;
    private final EmployeeLeaveMapper employeeLeaveMapper;
    private final EmployeeLeaveRepository employeeLeaveRepository;


    public EmployeeLeaveResponseDto requestLeave(EmployeeLeaveRequestDto dto, Long employeeId, Long companyId) {
        // 1. Çalışan doğrulama
        Employee employee = employeeService.findById(employeeId);
        if (!employee.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        // 2. Şirkete ait izin tipi bulunuyor
        CompanyLeaveType companyLeaveType = companyLeaveTypeService.findById(dto.companyLeaveTypeId());

        if (!companyLeaveType.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        LeaveType leaveType = leaveTypeService.findById(companyLeaveType.getLeaveTypeId());

        // 3. Gün kontrolü
        long requestedDays = ChronoUnit.DAYS.between(dto.startDate(), dto.endDate()) + 1;
        if (requestedDays > companyLeaveType.getDefaultDayCount()) {
            throw new HrWebsiteProjectException(ErrorType.LEAVE_DAY_LIMIT_EXCEEDED);
        }

        // 4. Kıdeme göre yıllık izin kontrolü
        if (leaveType.getName().startsWith("ANNUAL_LEAVE")) {
            long yearsWorked = ChronoUnit.YEARS.between(employee.getDateOfEmployment(), LocalDate.now());
            if (!isMatchingSeniorityLeaveType(leaveType.getName(), yearsWorked)) {
                throw new HrWebsiteProjectException(ErrorType.SENIORITY_LEAVE_MISMATCH);
            }
        }

        // 5. Mapper dönüşümü ve manuel setlemeler
        EmployeeLeave employeeLeave = employeeLeaveMapper.toEntity(dto);
        employeeLeave.setEmployeeId(employeeId);
        employeeLeave.setCompanyId(companyId);
        employeeLeave.setLeaveStatus(ELeaveStatus.PENDING);
        employeeLeave.setTotalDays((int) requestedDays);
//    employeeLeave.setLeaveTypeId(leaveType.getId());
        employeeLeave.setCompanyLeaveTypeId(companyLeaveType.getId());

        employeeLeaveRepository.save(employeeLeave);

        return employeeLeaveMapper.toResponseDto(employeeLeave, leaveType);
    }

    private boolean isMatchingSeniorityLeaveType(String leaveTypeName, long yearsWorked) {
        return (yearsWorked < 5 && leaveTypeName.contains("1-5")) ||
                (yearsWorked >= 5 && yearsWorked < 15 && leaveTypeName.contains("5-15")) ||
                (yearsWorked >= 15 && leaveTypeName.contains("15"));
    }

    public EmployeeLeaveResponseDto approveOrRejectLeave(LeaveApprovalRequestDto dto, Long companyId) {
        EmployeeLeave leave = employeeLeaveRepository.findById(dto.leaveRequestId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_REQUEST_NOT_FOUND));

        // Şirket doğrulaması
        if (!leave.getCompanyId().equals(companyId)) {
            throw new HrWebsiteProjectException(ErrorType.ACCESS_DENIED);
        }

        // Zaten işlenmişse tekrar onay/red yapılmasın
        if (!leave.getLeaveStatus().equals(ELeaveStatus.PENDING)) {
            throw new HrWebsiteProjectException(ErrorType.LEAVE_ALREADY_PROCESSED);
        }

        if (dto.approved()) {
            leave.setLeaveStatus(ELeaveStatus.APPROVED);
        } else {
            leave.setLeaveStatus(ELeaveStatus.REJECTED);
            leave.setReason(dto.rejectionReason()); // opsiyonel alan
        }

        employeeLeaveRepository.save(leave);

        CompanyLeaveType companyLeaveType = companyLeaveTypeService.findById(leave.getCompanyLeaveTypeId());
        LeaveType leaveType = leaveTypeService.findById(companyLeaveType.getLeaveTypeId());

        return employeeLeaveMapper.toResponseDto(leave, leaveType);
    }
}
