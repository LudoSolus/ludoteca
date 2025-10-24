package com.projectLudoteca.ludoteca.command.handler;

import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserHandler {

    private final UserRepository repository;

    public CreateUserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public User handle(CreateUserCommand command) {
        User user = new User();
        user.setName(command.name());
        user.setCpf(command.cpf());
        user.setEmail(command.email());
        user.setPassword(command.password());
        user.setRa(command.ra());
        user.setBirthDate(command.birthDate());
        user.setUserType(command.userRole());

        repository.save(user);

        return user;
    }
}
