package com.projectLudoteca.ludoteca.query.handler;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.query.model.EducationalInstitutionView;
import com.projectLudoteca.ludoteca.query.model.UserView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEducationalInstitutionsHandler {

    private final EducationalInstitutionRepository repository;

    public GetAllEducationalInstitutionsHandler(EducationalInstitutionRepository repository) {
        this.repository = repository;
    }

    public List<EducationalInstitutionView> getAllEducationalInstitutions() {
        return repository.findAll()
                .stream()
                .map(ei -> new EducationalInstitutionView(ei.getId().toString(), ei.getInstitutionName()))
                .toList();
    }

}
