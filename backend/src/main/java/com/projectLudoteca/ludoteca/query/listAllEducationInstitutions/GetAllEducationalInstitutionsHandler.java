package com.projectLudoteca.ludoteca.query.listAllEducationInstitutions;

import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEducationalInstitutionsHandler {

    private final EducationalInstitutionRepository repository;

    public GetAllEducationalInstitutionsHandler(EducationalInstitutionRepository repository) {
        this.repository = repository;
    }

    public List<GetAllEducationalInstitutionsView> getAllEducationalInstitutions() {
        return repository.findAll()
                .stream()
                .map(ei -> new GetAllEducationalInstitutionsView(ei.getId().toString(), ei.getInstitutionName(), ei.getIsUtfpr()))
                .toList();
    }

}
