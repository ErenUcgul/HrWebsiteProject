package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.ShiftTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShiftTrackingRepository extends JpaRepository<ShiftTracking, Long> {
    List<ShiftTracking> findAllByUserId(Long userId);
    List<ShiftTracking> findAllByShiftId(Long shiftId);
    List<ShiftTracking> findAllByUserIdAndShiftId(Long userId, Long shiftId);
    List<ShiftTracking> findAllByShiftIdAndUserId(Long shiftId, Long userId);


}
