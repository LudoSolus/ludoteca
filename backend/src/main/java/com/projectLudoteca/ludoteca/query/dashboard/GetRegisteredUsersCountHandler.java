package com.projectLudoteca.ludoteca.query.dashboard;

import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetRegisteredUsersCountHandler {

    private final UserRepository userRepository;

    public GetRegisteredUsersCountHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisteredUsersCountResponse handle() {
        Long count = userRepository.countByRemovedFalse();
        return new RegisteredUsersCountResponse(count);
    }
}
