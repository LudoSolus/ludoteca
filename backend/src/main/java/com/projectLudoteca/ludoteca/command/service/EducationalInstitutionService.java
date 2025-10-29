package com.projectLudoteca.ludoteca.command.service;

import com.projectLudoteca.ludoteca.command.handler.CreateEducationalInstitutionHandler;
import com.projectLudoteca.ludoteca.command.model.CreateEducationalInstitutionCommand;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationalInstitutionService {

    private final EducationalInstitutionRepository educationalInstitutionRepository;
    private final CreateEducationalInstitutionHandler createEducationalInstitutionHandler;

    @Autowired
    public EducationalInstitutionService(EducationalInstitutionRepository educationalInstitutionRepository, CreateEducationalInstitutionHandler createEducationalInstitutionHandler ) {
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.createEducationalInstitutionHandler = createEducationalInstitutionHandler;
    }

    public void createEducationalInstitution(CreateEducationalInstitutionCommand command) {
        if (command.institutionName() == null || command.institutionName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da instituição de ensino é obrigatório.");
        }

        createEducationalInstitutionHandler.handle(command);
    }

}
