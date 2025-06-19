package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.dto.response.UserStateInfoResponse;
import com.hrproject.hrwebsiteproject.model.entity.User;
import com.hrproject.hrwebsiteproject.model.enums.EUserState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    int countByState(EUserState state);

    int countByCreateAtGreaterThan(Long timestamp);

    List<User> findByState(EUserState state);

    boolean existsByPhone(String phone);
}
