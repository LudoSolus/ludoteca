package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.listAllEducationInstitutions.GetAllEducationalInstitutionsHandler;
import com.projectLudoteca.ludoteca.query.listAllEducationInstitutions.GetAllEducationalInstitutionsView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/educational-institutions")
public class EducationalInstitutionQueryController {

    private final GetAllEducationalInstitutionsHandler getAllEducationalInstitutionsHandler;

    public EducationalInstitutionQueryController(GetAllEducationalInstitutionsHandler getAllEducationalInstitutionsHandler) {
        this.getAllEducationalInstitutionsHandler = getAllEducationalInstitutionsHandler;
    }

    @GetMapping("/list-all-educational-institutions")
    public ResponseEntity<ApiResponse<List<GetAllEducationalInstitutionsView>>> getAllEducationalInstitutions() {

        List<GetAllEducationalInstitutionsView> institutions = getAllEducationalInstitutionsHandler.getAllEducationalInstitutions();
        ApiResponse<List<GetAllEducationalInstitutionsView>> response = new ApiResponse<>(institutions);

        return ResponseEntity.ok(response);
    }
}
