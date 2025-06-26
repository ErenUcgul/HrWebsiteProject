package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.Material;
import com.hrproject.hrwebsiteproject.model.enums.EMaterialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findAllByActiveTrue();

    boolean existsBySerialNumber(String serialNumber);

    Optional<Material> findByIdAndActiveTrue(Long id);

    List<Material> findAllByStatusAndActiveTrue(EMaterialStatus status);
}
