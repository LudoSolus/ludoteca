package com.projectLudoteca.ludoteca.command.updateUser;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UpdateUserHandler {

    private UserRepository userRepository;
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Autowired
    public UpdateUserHandler(UserRepository userRepository, EducationalInstitutionRepository educationalInstitutionRepository) {
        this.userRepository = userRepository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
    }

    @Transactional
    public String handle(User auth, UpdateUserCommand command) {
        if (isAllFieldsNull(command)) {
            throw new BusinessException("USR_001", "Pelo menos um campo deve ser informado para atualização.");
        }

        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new BusinessException("USR_002", "Usuário não encontrado."));

        if(Objects.equals(command.email(), user.getEmail())){
            throw new IllegalArgumentException("E-mail é igual ao mesmo já cadastrado!");
        }

        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        if (command.ra() != null && !command.ra().matches("\\d+")) {
            throw new BusinessException("400", "O RA deve conter apenas números.");
        }

        // Atualiza apenas os campos que não são nulos
        if (command.name() != null) user.setName(command.name());
        if (command.email() != null) user.setEmail(command.email());
        if (command.phone() != null) user.setPhone(command.phone());
        if (command.ra() != null) user.setRa(command.ra());
        if (command.institutionId() != null) {

            EducationalInstitution institution = educationalInstitutionRepository.findById(UUID.fromString(command.institutionId()))
                    .orElseThrow(() -> new BusinessException("EDI_001", "Instituição não encontrada."));

            user.setEducationalInstitution(institution);
        }

        // Salva o usuário atualizado
        userRepository.save(user);

        return "Usuário atualizado com sucesso!";
    }

    private boolean isAllFieldsNull(UpdateUserCommand command) {
        return command.name() == null &&
                command.email() == null &&
                command.phone() == null &&
                command.ra() == null &&
                command.institutionId() == null;
    }
}
