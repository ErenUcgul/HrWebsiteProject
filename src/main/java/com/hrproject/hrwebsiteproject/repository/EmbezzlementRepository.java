package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.Embezzlement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmbezzlementRepository extends JpaRepository<Embezzlement, Long> {

    List<Embezzlement> findAllByManagerIdAndActiveTrue(
            @NotNull(message = "Yönetici ID'si boş olamaz.") Long managerId
    );

    //public List<Embezzlement> findAllByUserIdAndActiveTrue(Long userId);

    List<Embezzlement> findAllByUserIdAndActiveTrue(
            @NotNull(message = "Kullanıcı ID'si boş olamaz.") Long userId
    );

    Optional<Embezzlement> findById(
            @NotNull(message = "Zimmet ID'si boş olamaz.") Long id
    );


    boolean existsByMaterialIdAndIsReturnedFalseAndActiveTrueAndIdNot(Long materialId, Long id);

    boolean existsByMaterialIdAndIsReturnedFalseAndActiveTrue(Long aLong);

    List<Embezzlement> findAllByManagerIdAndActiveFalse(Long managerId);

    List<Embezzlement> findAllByManagerId(Long managerId);

    Optional<Embezzlement> findByIdAndActiveTrue(Long id);

    List<Embezzlement> findAllByUserIdIn(List<Long> userIds);

    

}