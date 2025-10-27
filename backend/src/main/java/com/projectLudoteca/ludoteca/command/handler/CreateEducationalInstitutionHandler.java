package com.projectLudoteca.ludoteca.command.handler;

import com.projectLudoteca.ludoteca.command.model.CreateEducationalInstitutionCommand;
import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateEducationalInstitutionHandler {

    private final EducationalInstitutionRepository repository;

    public CreateEducationalInstitutionHandler(EducationalInstitutionRepository repository ) {
        this.repository = repository;
    }

    public void handle(CreateEducationalInstitutionCommand command) {
        EducationalInstitution educationalInstitution = new EducationalInstitution(command.institutionName());
        repository.save(educationalInstitution);
    }

}
