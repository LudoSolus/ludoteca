package com.projectLudoteca.ludoteca.command.changeRoleUser;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChangeRoleUserHandler {

    private final UserRepository userRepository;

    public ChangeRoleUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handle(UUID userId, ChangeRoleUserCommand command) {
        User user = userRepository.findUserNativeAndRemovedFalse(userId).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));


        if (command.isAdmin() == null) {
            throw new IllegalArgumentException("Requisição não pode ser nula ou vazia.");
        }

        UserRole newRole;

        if(command.isAdmin()){
            newRole = UserRole.ADMIN;
        } else if(user.getRa() != null){
            newRole = UserRole.STUDENT;
        } else {
            newRole = UserRole.USER;
        }

        if (newRole == user.getUserRole()) {
            throw new IllegalArgumentException("A nova role é a mesma que a role atual do usuário. Nenhuma alteração é necessária.");
        }

        user.setUserRole(newRole);
        userRepository.save(user);

        return "Role alterada com sucesso!";
    }

}
