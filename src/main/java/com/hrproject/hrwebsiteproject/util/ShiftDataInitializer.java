package com.hrproject.hrwebsiteproject.util;
import com.hrproject.hrwebsiteproject.model.dto.request.ShiftRequestDto;
import com.hrproject.hrwebsiteproject.service.ShiftService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShiftDataInitializer {

    private final ShiftService shiftService;
    @PostConstruct
    public void init() {

        List<ShiftRequestDto> company1Sfihts = List.of(
                new ShiftRequestDto("Sabah Vardiyası", LocalTime.of(6, 0), LocalTime.of(14, 0),1L),
                new ShiftRequestDto("Öğlen Vardiyası", LocalTime.of(14, 0), LocalTime.of(22, 0), 1L),
                new ShiftRequestDto("Gece Vardiyası", LocalTime.of(22, 0), LocalTime.of(6, 0), 1L)
        );
        List<ShiftRequestDto> company2Shifts = List.of(
                new ShiftRequestDto("Sabah Vardiyası", LocalTime.of(7, 0), LocalTime.of(15, 0), 2L),
                new ShiftRequestDto("Öğlen Vardiyası", LocalTime.of(15, 0), LocalTime.of(23, 0), 2L),
                new ShiftRequestDto("Gece Vardiyası", LocalTime.of(23, 0), LocalTime.of(7, 0), 2L)
        );
        shiftService.createAndCheckShift(company1Sfihts);
        shiftService.createAndCheckShift(company2Shifts);
    }
}

