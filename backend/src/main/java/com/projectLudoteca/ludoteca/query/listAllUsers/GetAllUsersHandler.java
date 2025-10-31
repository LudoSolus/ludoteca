package com.projectLudoteca.ludoteca.query.listAllUsers;

import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersHandler {

    private final UserRepository repository;

    public GetAllUsersHandler(UserRepository repository) {
        this.repository = repository;
    }

    public List<GetAllUsersView> handleGetAllUsers() {
        return repository.findAll()
                         .stream()
                         .map(u -> new GetAllUsersView(u.getPublicId(), u.getName(), u.getCpf(), u.getEmail(), u.getRa(), u.getBirthDate().toString()))
                         .toList();
    }
}
