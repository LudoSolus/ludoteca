package com.projectLudoteca.ludoteca.command.registerEducationalInstitution;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateEducationalInstitutionHandler {

    private final EducationalInstitutionRepository repository;

    @Autowired
    public CreateEducationalInstitutionHandler(EducationalInstitutionRepository repository ) {
        this.repository = repository;
    }

    @Transactional
    public void handle(CreateEducationalInstitutionCommand command) {
        if (command.institutionName() == null || command.institutionName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da instituição de ensino é obrigatório.");
        }

        EducationalInstitution educationalInstitution = new EducationalInstitution(command.institutionName());


        repository.save(educationalInstitution);


    }

}
