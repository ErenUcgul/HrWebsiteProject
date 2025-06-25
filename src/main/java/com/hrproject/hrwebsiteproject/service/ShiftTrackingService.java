package com.hrproject.hrwebsiteproject.service;
import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.AssignShiftRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.EmployeeShiftResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.Shift;
import com.hrproject.hrwebsiteproject.model.entity.ShiftTracking;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.repository.ShiftRepository;
import com.hrproject.hrwebsiteproject.repository.ShiftTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftTrackingService {
    private final ShiftTrackingRepository shiftTrackingRepository;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final ShiftRepository shiftRepository;

    public Boolean assignShiftToEmployee(Long employeeId, AssignShiftRequestDto dto) {
        // 1. Employee kontrolü
        Employee employee = employeeService.findById(employeeId);
        Long userId = employee.getUserId();
        // 2. Vardiya kontrolü
        Shift shift = shiftRepository.findById(dto.shiftId())
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SHIFT_NOT_FOUND));
        // 3. Tarih aralığı kontrolü
        if (dto.startDate().isAfter(dto.endDate())) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_DATE_RANGE);
        }
        // 4. Aynı vardiya aynı tarih aralığında başka çalışana atanmış mı?
        List<ShiftTracking> sameShiftAssignments = shiftTrackingRepository.findAllByShiftId(dto.shiftId());
        for (ShiftTracking tracking : sameShiftAssignments) {
            if (!tracking.getUserId().equals(userId)) {
                if (isDateOverlap(dto.startDate(), dto.endDate(), tracking.getBeginDate(), tracking.getEndDate())) {
                    throw new HrWebsiteProjectException(ErrorType.SHIFT_ALREADY_ASSIGNED_TO_ANOTHER_EMPLOYEE);
                }
            }
        }
        // 5. Aynı personelin tarih çakışması kontrolü (başka shift’lerle)
        List<ShiftTracking> userShifts = shiftTrackingRepository.findAllByUserId(userId);
        for (ShiftTracking existing : userShifts) {
            if (isDateOverlap(dto.startDate(), dto.endDate(), existing.getBeginDate(), existing.getEndDate())) {
                throw new HrWebsiteProjectException(ErrorType.SHIFT_DATE_CONFLICT);
            }
        }
        // 6. Atama işlemi
        ShiftTracking tracking = ShiftTracking.builder()
                .shiftId(dto.shiftId())
                .userId(userId)
                .beginDate(dto.startDate())
                .endDate(dto.endDate())
                .build();

        shiftTrackingRepository.save(tracking);
        return true;
    }

    public Boolean updateAssignedShift(Long trackingId, AssignShiftRequestDto dto) {
        // 1. Mevcut atama bulunuyor mu?
        ShiftTracking tracking = shiftTrackingRepository.findById(trackingId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SHIFT_ASSIGNMENT_NOT_FOUND));
        Long userId = tracking.getUserId();
        // 2. Yeni değerler (null değilse alınır)
        Long newShiftId = dto.shiftId() != null ? dto.shiftId() : tracking.getShiftId();
        LocalDate newStart = dto.startDate() != null ? dto.startDate() : tracking.getBeginDate();
        LocalDate newEnd = dto.endDate() != null ? dto.endDate() : tracking.getEndDate();
        // 3. Tarih aralığı kontrolü
        if (newStart.isAfter(newEnd)) {
            throw new HrWebsiteProjectException(ErrorType.INVALID_DATE_RANGE);
        }
        // 4. Aynı vardiya başka çalışana aynı tarihlerde atanmış mı?
        List<ShiftTracking> sameShiftAssignments = shiftTrackingRepository.findAllByShiftId(newShiftId);
        for (ShiftTracking other : sameShiftAssignments) {
            if (!other.getUserId().equals(userId) && !other.getId().equals(trackingId)) {
                if (isDateOverlap(newStart, newEnd, other.getBeginDate(), other.getEndDate())) {
                    throw new HrWebsiteProjectException(ErrorType.SHIFT_ALREADY_ASSIGNED_TO_ANOTHER_EMPLOYEE);
                }
            }
        }
        // 5. Kullanıcının kendi diğer vardiyalarıyla tarih çakışması kontrolü
        List<ShiftTracking> userShifts = shiftTrackingRepository.findAllByUserId(userId).stream()
                .filter(st -> !st.getId().equals(trackingId))
                .toList();
        for (ShiftTracking existing : userShifts) {
            if (isDateOverlap(newStart, newEnd, existing.getBeginDate(), existing.getEndDate())) {
                throw new HrWebsiteProjectException(ErrorType.SHIFT_DATE_CONFLICT);
            }
        }
        // 6. Güncelleme işlemi
        tracking.setShiftId(newShiftId);
        tracking.setBeginDate(newStart);
        tracking.setEndDate(newEnd);
        shiftTrackingRepository.save(tracking);
        return true;
    }

    private boolean isDateOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }
    public Boolean deleteAssignedShift(Long trackingId) {
        ShiftTracking tracking = shiftTrackingRepository.findById(trackingId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SHIFT_ASSIGNMENT_NOT_FOUND));
        shiftTrackingRepository.delete(tracking);
        return true;
    }
    public List<EmployeeShiftResponseDto> getShiftsByEmployeeId(Long employeeId) {
        Employee employee = employeeService.findById(employeeId); // employeeId'den employee bulunur
        Long userId = employee.getUserId(); // Employee içinden userId alınır
        List<ShiftTracking> trackingList = shiftTrackingRepository.findAllByUserId(userId);
        if (trackingList.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.SHIFT_ASSIGNMENT_NOT_FOUND);
        }
        User user = userService.findById(userId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
        return trackingList.stream()
                .map(t -> new EmployeeShiftResponseDto(
                        employeeId,
                        userId,
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        t.getBeginDate(),
                        t.getEndDate()
                )).toList();
    }

    public List<EmployeeShiftResponseDto> getEmployeesByShiftId(Long shiftId) {
        List<ShiftTracking> trackingList = shiftTrackingRepository.findAllByShiftId(shiftId);
        if (trackingList.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.SHIFT_ASSIGNMENT_NOT_FOUND);
        }
        return trackingList.stream()
                .map(t -> {
                    Long userId = t.getUserId();
                    User user = userService.findById(userId)
                            .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.USER_NOT_FOUND));
                    Long employeeId = employeeService.getEmployeeIdByUserId(userId);
                    return new EmployeeShiftResponseDto(
                            employeeId,
                            userId,
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            t.getBeginDate(),
                            t.getEndDate()
                    );
                }).toList();
    }
}
