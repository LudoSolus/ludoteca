package com.projectLudoteca.ludoteca.command.controller.userAcess;

import com.projectLudoteca.ludoteca.command.registerEducationalInstitution.CreateEducationalInstitutionCommand;
import com.projectLudoteca.ludoteca.command.registerEducationalInstitution.CreateEducationalInstitutionHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User - Instituições Educacionais", description = "Rotas para cadastro de instituições de ensino")
public class EducationalInstitutionCommandController {

    private final CreateEducationalInstitutionHandler createEducationalInstitutionHandler;

    public EducationalInstitutionCommandController(CreateEducationalInstitutionHandler createEducationalInstitutionHandler) {
        this.createEducationalInstitutionHandler = createEducationalInstitutionHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nova instituição", description = "Cria o registro de uma nova instituição educacional no sistema.")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateEducationalInstitutionCommand command) {
        createEducationalInstitutionHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>("Instituição registrada com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}