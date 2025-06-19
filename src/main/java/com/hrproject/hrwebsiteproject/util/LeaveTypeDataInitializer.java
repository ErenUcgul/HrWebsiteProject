package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.entity.LeaveType;
import com.hrproject.hrwebsiteproject.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LeaveTypeDataInitializer implements ApplicationRunner {

    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<LeaveType> leaveTypes = List.of(
                new LeaveType("ANNUAL_LEAVE 1-5 YEARS", "1-5 Yıl Arası Çalışan Yıllık izin"),
                new LeaveType("ANNUAL_LEAVE 5-15 YEARS", "5-15 Yıl Arası Çalışan Yıllık izin"),
                new LeaveType("ANNUAL_LEAVE 15 + YEARS", "15 Yıl'dan Fazla Çalışan Yıllık izin"),
                new LeaveType("SICK_LEAVE", "Hastalık izni"),
                new LeaveType("CASUAL_LEAVE", "Mazeret izni"),
                new LeaveType("MATERNITY_LEAVE", "Doğum izni (kadın çalışanlar için)"),
                new LeaveType("PATERNITY_LEAVE", "Babalık izni"),
                new LeaveType("PUBLIC_HOLIDAY", "Resmi tatil izni"),
                new LeaveType("RELIGIOUS_HOLIDAY", "Dini bayram izni"),
                new LeaveType("COMPENSATORY_LEAVE", "Fazla mesaiye karşılık izin"),
                new LeaveType("UNPAID_LEAVE", "Ücretsiz izin"),
                new LeaveType("STUDY_LEAVE", "Eğitim/öğrenim izni"),
                new LeaveType("SABBATICAL", "Uzun süreli kariyer molası"),
                new LeaveType("EMERGENCY_LEAVE", "Acil durum izni"),
                new LeaveType("MARRIAGE_LEAVE", "Evlilik izni"),
                new LeaveType("FUNERAL_LEAVE", "Cenaze izni"),
                new LeaveType("OTHER", "Diğer nedenlerle verilen izin"),
                new LeaveType("BREASTFEEDING_LEAVE", "Süt izni (anneler için)"),
                new LeaveType("JOB_SEARCH_LEAVE", "Yeni iş arama izni (işten çıkarılma sonrası)"),
                new LeaveType("WEEKEND_LEAVE", "Hafta tatili izni"),
                new LeaveType("ADOPTION_LEAVE", "Evlat edinme izni"),
                new LeaveType("COMPANION_LEAVE", "Refakat izni"),
                new LeaveType("TRAVEL_LEAVE", "Yol izni"),
                new LeaveType("MILITARY_LEAVE", "Askerlik izni"),
                new LeaveType("MOVING_LEAVE", "Taşınma izni"),
                new LeaveType("BIRTHDAY_LEAVE", "Doğum günü izni"),
                new LeaveType("DOCTOR_VISIT_LEAVE", "Vizite (doktor kontrolü) izni"),
                new LeaveType("MENSTRUAL_LEAVE", "Adet dönemi izni"),
                new LeaveType("NO_SMOKING_LEAVE", "Sigara içmeyen çalışan izni")
        );

        leaveTypes.forEach(type -> {
            boolean exists = leaveTypeRepository.existsByName(type.getName());
            if (!exists) {
                leaveTypeRepository.save(type);
            }
        });
    }
}
