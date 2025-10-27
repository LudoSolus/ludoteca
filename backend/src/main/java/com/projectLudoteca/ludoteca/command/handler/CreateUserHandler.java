package com.projectLudoteca.ludoteca.command.handler;

import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserHandler {

    private final UserRepository repository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;

    public CreateUserHandler(UserRepository repository, EducationalInstitutionRepository educationalInstitutionRepository) {
        this.repository = repository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
    }

    public User handle(CreateUserCommand command) {
        EducationalInstitution  educationalInstitution = new EducationalInstitution();

        User user = new User();
        user.setName(command.name());
        user.setCpf(command.cpf());
        user.setEmail(command.email());
        user.setPassword(command.password());
        user.setPhone(command.phone());
        user.setRa(command.ra());
        user.setBirthDate(command.birthDate());
        user.setUserType(command.userRole());

        if (command.institutionId() != null && !command.institutionId().isBlank()) {
            educationalInstitutionRepository.findById(UUID.fromString(command.institutionId()))
                    .ifPresent(user::setEducationalInstitution);
        }

        repository.save(user);

        return user;
    }
}
