package com.projectLudoteca.ludoteca.command.service;

import com.projectLudoteca.ludoteca.command.handler.CreateUserHandler;
import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.model.LoginUserCommand;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserCommandService {

    private final CreateUserHandler createUserHandler;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserCommandService(CreateUserHandler createUserHandler, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.createUserHandler = createUserHandler;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public String createUser(CreateUserCommand command) {

        if (command.name() == null || command.name().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (command.cpf() == null || command.cpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }
        if (command.email() == null || command.email().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (command.password() == null || command.password().trim().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(emailRegex, command.email())) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }

        String cpfRegex = "\\d{11}";
        if (!Pattern.matches(cpfRegex, command.cpf())) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos.");
        }

        String senhaRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!Pattern.matches(senhaRegex, command.password())) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 8 caracteres, incluindo letras e números.");
        }

        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        if (userRepository.existsByCpf(command.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        String encodedPassword = passwordEncoder.encode(command.password());

        CreateUserCommand encodedCommand = new CreateUserCommand(
                command.name(),
                command.cpf(),
                command.email(),
                encodedPassword,
                command.ra()
        );

        User user = createUserHandler.handle(encodedCommand);

        String token = jwtService.generateToken(user.getPublicId(), user.getEmail());

        return token;
    }


    public String login(LoginUserCommand command) {

        User user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.generateToken(user.getPublicId(), user.getEmail());
    }

}
