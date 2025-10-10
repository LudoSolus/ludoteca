package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.handler.CreateUserHandler;
import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.common.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/users")
public class UserCommandController {

    private final CreateUserHandler handler;

    public UserCommandController(CreateUserHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody CreateUserCommand command) {
        handler.handle(command);
        return ResponseEntity.ok("Usuário adicionado com sucesso!");
    }
}
