package com.projectLudoteca.ludoteca.query.controller;

import com.projectLudoteca.ludoteca.query.handler.GetAllUsersHandler;
import com.projectLudoteca.ludoteca.query.model.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/students")
public class UserQueryController {

    private final GetAllUsersHandler handler;

    public UserQueryController(GetAllUsersHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/list-all-users")
    public ResponseEntity<List<UserView>> getAllUsers() {
        return ResponseEntity.ok(handler.handleGetAllUsers());
    }

}
