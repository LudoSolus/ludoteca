package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByRaAndIdNot(String ra, UUID id);

    boolean existsByCpf(String cpf);

    Optional<User> findByIdAndRemovedFalse(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPublicId(String publicId);

    boolean existsByRa(String ra);

    @Query(value = "SELECT * FROM users WHERE id = ?1 AND removed = false", nativeQuery = true)
    Optional<User> findUserNativeAndRemovedFalse(UUID userId);

    Optional<User> findByPublicIdAndRemovedFalse(String publicId);

    Long countByRemovedFalse();
}
