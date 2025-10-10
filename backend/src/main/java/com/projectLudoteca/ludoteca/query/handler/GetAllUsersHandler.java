package com.projectLudoteca.ludoteca.query.handler;

import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.query.model.UserView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersHandler {

    private final UserRepository repository;

    public GetAllUsersHandler(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserView> handleGetAllUsers() {
        return repository.findAll()
                         .stream()
                         .map(u -> new UserView(u.getName(), u.getCpf(), u.getEmail(), u.getRa()))
                         .toList();
    }
}
