package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.PasswordReset;
import com.projectLudoteca.ludoteca.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    Optional<PasswordReset> findByCode(String code);

    Optional<PasswordReset> findTopByUserAndUsedFalseOrderByCreatedAtDesc(User user);

    Optional<PasswordReset> findByCodeAndUsedFalse(String code);

}
