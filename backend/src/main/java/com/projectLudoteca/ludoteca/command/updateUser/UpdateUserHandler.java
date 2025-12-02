package com.projectLudoteca.ludoteca.command.updateUser;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.infrastructure.security.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UpdateUserHandler {

    private final UserRepository userRepository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;
    private final JwtService jwtService;

    @Autowired
    public UpdateUserHandler(UserRepository userRepository,
                             EducationalInstitutionRepository educationalInstitutionRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public String handle(User auth, UpdateUserCommand command) {
        if (isAllFieldsNull(command)) {
            throw new BusinessException("USR_001", "Pelo menos um campo deve ser informado para atualização.");
        }

        User user = userRepository.findUserNativeAndRemovedFalse(auth.getId())
                .orElseThrow(() -> new BusinessException("USR_002", "Usuário não encontrado."));

        if (!Objects.equals(command.email(), user.getEmail()) && userRepository.existsByEmailAndIdNot(command.email(), user.getId())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        EducationalInstitution educationalInstitution = null;
        if (command.institutionId() != null) {
            educationalInstitution = educationalInstitutionRepository.findById(UUID.fromString(command.institutionId()))
                    .orElseThrow(() -> new BusinessException("USR_002", "Instituição não encontrada."));
        }

        if (educationalInstitution != null) {
            user.setEducationalInstitution(educationalInstitution);
        }
        if (user.getEducationalInstitution() != null) {
            if (user.getEducationalInstitution().getIsUtfpr()) {
                if (command.ra() == null) {
                    throw new BusinessException("400", "O RA é obrigatório para alunos da UTFPR.");
                }

                if (!command.ra().matches("\\d+")) {
                    throw new BusinessException("400", "O RA deve conter apenas números.");
                }
            }
            if (user.getEducationalInstitution().getIsUtfpr()) {
                if (userRepository.existsByRaAndIdNot(command.ra(), user.getId())) {
                    throw new BusinessException("400", "O RA já cadastrado!");
                }
                user.setRa(command.ra());
            } else {
                user.setRa(null);
            }
        }

        if (command.name() != null)
            user.setName(command.name());
        if (command.email() != null)
            user.setEmail(command.email());
        if (command.phone() != null)
            user.setPhone(command.phone());

        userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getName(), user.getPublicId(), user.getEmail(),
                user.getUserRole().name());

        return token;
    }

    private boolean isAllFieldsNull(UpdateUserCommand command) {
        return command.name() == null &&
                command.email() == null &&
                command.phone() == null &&
                command.ra() == null &&
                command.institutionId() == null;
    }
}
