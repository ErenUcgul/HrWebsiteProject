package com.hrproject.hrwebsiteproject.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.hrproject.hrwebsiteproject.model.entity.Employee;
import com.hrproject.hrwebsiteproject.model.enums.EEmploymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    boolean existsByIdentityNo(@NotBlank(message = "TC kimlik numarası boş olamaz.") @Size(min = 11, max = 11, message = "TC kimlik numarası 11 haneli olmalıdır.") String identityNo);

    boolean existsBySocialSecurityNumber(@NotBlank(message = "SGK numarası boş olamaz.") String socialSecurityNumber);

    List<Employee> findAllByCompanyId(Long companyId);

    Optional<Employee> findByUserId(Long userId);

    long countByCompanyId(Long companyId);

    // Aktif personel sayısı için (employmentStatus ACTIVE olanlar)
    long countByCompanyIdAndEmploymentStatus(Long companyId, EEmploymentStatus employmentStatus);

    // Belirli bir işe başlama tarihine sahip personel sayısı
    long countByCompanyIdAndDateOfEmployment(Long companyId, LocalDate date);

    // Belirli bir işten ayrılma tarihine sahip personel sayısı
    long countByCompanyIdAndDateOfTermination(Long companyId, LocalDate date);
}
