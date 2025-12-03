package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, UUID> {
}
