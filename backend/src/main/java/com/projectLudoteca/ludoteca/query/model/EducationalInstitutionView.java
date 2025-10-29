package com.projectLudoteca.ludoteca.query.model;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EducationalInstitutionView {
    private String institutionId;
    private String institutionName;
}
