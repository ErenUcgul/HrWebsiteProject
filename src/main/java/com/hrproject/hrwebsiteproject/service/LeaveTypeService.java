package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.mapper.LeaveTypeMapper;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeCreateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.LeaveTypeUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.LeaveTypeResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import com.hrproject.hrwebsiteproject.repository.LeaveTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;

    public LeaveTypeResponseDto create(LeaveTypeCreateRequestDto dto) {
        if (leaveTypeRepository.existsByNameIgnoreCase(dto.name())) {
            throw new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_ALREADY_EXISTS);
        }

        LeaveType leaveType = leaveTypeMapper.toEntity(dto);
        return leaveTypeMapper.toResponseDto(leaveTypeRepository.save(leaveType));
    }

    public LeaveTypeResponseDto update(Long id, LeaveTypeUpdateRequestDto dto) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_FOUND));

        leaveTypeMapper.updateFromDto(dto, leaveType);
        return leaveTypeMapper.toResponseDto(leaveTypeRepository.save(leaveType));
    }

    public void delete(Long id) {
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_FOUND));
        leaveTypeRepository.delete(leaveType);
    }

    public List<LeaveTypeResponseDto> findAll() {
        return leaveTypeRepository.findAll().stream()
                .map(leaveTypeMapper::toResponseDto)
                .toList();
    }
    public LeaveType findById(Long id) {
        return leaveTypeRepository.findById(id)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.LEAVE_TYPE_NOT_FOUND));
    }
}
