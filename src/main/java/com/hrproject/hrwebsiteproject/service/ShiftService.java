package com.hrproject.hrwebsiteproject.service;

import com.hrproject.hrwebsiteproject.exceptions.ErrorType;
import com.hrproject.hrwebsiteproject.exceptions.HrWebsiteProjectException;
import com.hrproject.hrwebsiteproject.model.dto.request.ShiftRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.ShiftUpdateRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.response.ShiftResponseDto;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.entity.Shift;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final CompanyService companyService;

    public Boolean createAndCheckShift(List<ShiftRequestDto> dtoList) {
        // Her DTO için süre hesaplanıp toplam kontrolü yapılır
        long newShiftsTotalMinutes = dtoList.stream()
                .mapToLong(dto -> {
                    long duration = Duration.between(dto.shiftStart(), dto.shiftEnd()).toMinutes();
                    return duration >= 0 ? duration : duration + 24 * 60;
                }).sum();

        // Aynı şirkete ait mevcut vardiyalar bulunur (ilk dto'dan alınan companyId ile)
        Long companyId = dtoList.get(0).companyId(); // tüm dto'ların aynı şirkete ait olduğu varsayılıyor
        List<Shift> allShifts = shiftRepository.findAllByCompanyId(companyId);

        long existingTotalMinutes = allShifts.stream()
                .mapToLong(shift -> {
                    long duration = Duration.between(shift.getBeginHour(), shift.getEndHour()).toMinutes();
                    return duration >= 0 ? duration : duration + 24 * 60;
                }).sum();

        if (existingTotalMinutes + newShiftsTotalMinutes > 24 * 60) {
            throw new HrWebsiteProjectException(ErrorType.OUT_OF_BOUNDARY_SHIFT_HOURS);
        }
        // Eğer toplam süre sınırı aşılmıyorsa kayıt işlemi yapılır
        List<Shift> shifts = dtoList.stream()
                .map(dto -> Shift.builder()
                        .shiftName(dto.shiftName())
                        .beginHour(dto.shiftStart())
                        .endHour(dto.shiftEnd())
                        .companyId(dto.companyId())
                        .build())
                .collect(Collectors.toList());
        shiftRepository.saveAll(shifts);
        return true;
    }

    public List<ShiftResponseDto> getShiftsByCompanyId(Long companyId) {
        // Company kontrolü (var mı?)
        companyService.findByCompanyId(companyId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.COMPANY_NOT_FOUND));
        // Şirketin vardiyaları
        List<Shift> shifts = shiftRepository.findAllByCompanyId(companyId);
        if (shifts.isEmpty()) {
            throw new HrWebsiteProjectException(ErrorType.SHIFT_NOT_FOUND);
        }
        return shifts.stream()
                .map(shift -> new ShiftResponseDto(
                        shift.getId(),
                        shift.getShiftName(),
                        shift.getBeginHour(),
                        shift.getEndHour()))
                .toList();
    }
    public Boolean deleteShift(Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SHIFT_NOT_FOUND));

        shiftRepository.delete(shift);

        return true;
    }

    public Boolean updateShift(Long shiftId, ShiftUpdateRequestDto dto) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new HrWebsiteProjectException(ErrorType.SHIFT_NOT_FOUND));
        // Geçerli companyId
        Long companyId = shift.getCompanyId();
        // Yeni değerler, null ise eski değer kalacak
        String newName = dto.shiftName() != null ? dto.shiftName() : shift.getShiftName();
        LocalTime newStart = dto.shiftStart() != null ? dto.shiftStart() : shift.getBeginHour();
        LocalTime newEnd = dto.shiftEnd() != null ? dto.shiftEnd() : shift.getEndHour();
        // Süre kontrolü
        long duration = Duration.between(newStart, newEnd).toMinutes();
        if (duration < 0) duration += 24 * 60;
        if (duration > 24 * 60) {
            throw new HrWebsiteProjectException(ErrorType.OUT_OF_BOUNDARY_SHIFT_HOURS);
        }
        // Çakışma kontrolü (kendisi hariç)
        List<Shift> shifts = shiftRepository.findAllByCompanyId(companyId);
        for (Shift other : shifts) {
            if (other.getId().equals(shiftId)) continue;

            LocalTime otherStart = other.getBeginHour();
            LocalTime otherEnd = other.getEndHour();

            if (isOverlap(newStart, newEnd, otherStart, otherEnd)) {
                throw new HrWebsiteProjectException(ErrorType.SHIFT_TIME_CONFLICT);
            }
        }
        // Güncelle
        shift.setShiftName(newName);
        shift.setBeginHour(newStart);
        shift.setEndHour(newEnd);

        shiftRepository.save(shift);
        return true;
    }

    // Zaman aralıklarının çakışma kontrolü
    private boolean isOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // Saatler arasında çakışma varsa true döner
        // Yardımcı metod örneği:
        long s1 = start1.toSecondOfDay();
        long e1 = end1.toSecondOfDay();
        long s2 = start2.toSecondOfDay();
        long e2 = end2.toSecondOfDay();
        // Saat aralığı 24 saatin içinde döngüyse (ör. gece yarısından sonra biterse)
        if (e1 <= s1) e1 += 24 * 3600;
        if (e2 <= s2) e2 += 24 * 3600;
        return s1 < e2 && s2 < e1;
    }

}
