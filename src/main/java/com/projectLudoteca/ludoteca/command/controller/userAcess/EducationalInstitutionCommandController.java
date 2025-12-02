package com.projectLudoteca.ludoteca.command.controller.userAcess;

import com.projectLudoteca.ludoteca.command.registerEducationalInstitution.CreateEducationalInstitutionCommand;
import com.projectLudoteca.ludoteca.command.registerEducationalInstitution.CreateEducationalInstitutionHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/educational-institutions")
@Validated
public class EducationalInstitutionCommandController {

    private CreateEducationalInstitutionHandler createEducationalInstitutionHandler;

    public EducationalInstitutionCommandController(CreateEducationalInstitutionHandler createEducationalInstitutionHandler) {
        this.createEducationalInstitutionHandler = createEducationalInstitutionHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateEducationalInstitutionCommand command) {
        createEducationalInstitutionHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>("Instituição registrada com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
