package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.CompanyLeaveTypeMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.CompanyLeaveTypeRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.CompanyLeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.CompanyLeaveType;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import com.hrproject.hrwebsiteproject.repository.CompanyLeaveTypeRepository;
import com.hrproject.hrwebsiteproject.repository.LeaveTypeRepository;
import com.hrproject.hrwebsiteproject.util.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyLeaveTypeService {
    private final CompanyLeaveTypeRepository companyLeaveTypeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final JwtManager jwtManager;
    private final CompanyLeaveTypeMapper companyLeaveTypeMapper;
    private final CompanyService companyService;

    public void assignLeaveTypeToCompany(String token, CompanyLeaveTypeRequestDto dto) {
        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = getCompanyIdByUserId(userId);

        if (!leaveTypeRepository.existsById(dto.leaveTypeId())) {
            throw new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_FOUND);
        }

        if (companyLeaveTypeRepository.existsByCompanyIdAndLeaveTypeId(companyId, dto.leaveTypeId())) {
            throw new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_ALREADY_ASSIGNED_TO_COMPANY);
        }

        CompanyLeaveType entity = CompanyLeaveType.builder()
                .companyId(companyId)
                .leaveTypeId(dto.leaveTypeId())
                .defaultDayCount(dto.defaultDayCount())
                .requiresApproval(dto.requiresApproval())
                .active(true)
                .build();

        companyLeaveTypeRepository.save(entity);
    }

    public List<CompanyLeaveTypeResponseDto> getCompanyLeaveTypes(String token) {
        Long userId = jwtManager.getUserIdFromToken(token);
        Long companyId = getCompanyIdByUserId(userId);

        List<CompanyLeaveType> list = companyLeaveTypeRepository.findAllByCompanyId(companyId);

        return list.stream().map(clt -> {
            LeaveType leaveType = leaveTypeRepository.findById(clt.getLeaveTypeId())
                    .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_FOUND));
            return companyLeaveTypeMapper.toResponseDto(clt, leaveType);
        }).toList();
    }

    private Long getCompanyIdByUserId(Long userId) {
        return companyService.getCompanyIdByUserId(userId);
    }

    public CompanyLeaveType findByCompanyIdAndLeaveTypeId(Long companyId, Long leaveTypeId) {
        return companyLeaveTypeRepository.findByCompanyIdAndLeaveTypeId(companyId, leaveTypeId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_ALLOWED));
    }

    public CompanyLeaveType findById(Long id) {
        return companyLeaveTypeRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_LEAVE_TYPE_NOT_FOUND));
    }
}
