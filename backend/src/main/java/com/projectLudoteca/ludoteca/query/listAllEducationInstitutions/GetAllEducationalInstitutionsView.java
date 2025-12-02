package com.projectLudoteca.ludoteca.query.listAllEducationInstitutions;

public record GetAllEducationalInstitutionsView(
        String institutionId,
        String institutionName,
        Boolean isUtfpr
) {}
