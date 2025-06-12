package com.hrproject.hrwebsiteproject.repository;

import com.hrproject.hrwebsiteproject.model.entity.StaticContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaticContentRepository extends JpaRepository<StaticContent, Long> {
    Optional<StaticContent> findByKey(String key);
}
