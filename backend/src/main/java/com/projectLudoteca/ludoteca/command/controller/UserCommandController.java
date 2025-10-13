package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.model.LoginUserCommand;
import com.projectLudoteca.ludoteca.command.service.UserCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/users")
public class UserCommandController {

    private final UserCommandService service;

    public UserCommandController(UserCommandService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody CreateUserCommand command) {
        String response = service.createUser(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> createUser(@RequestBody LoginUserCommand command) {
        String response = service.login(command);
        return ResponseEntity.ok(response);
    }
}
