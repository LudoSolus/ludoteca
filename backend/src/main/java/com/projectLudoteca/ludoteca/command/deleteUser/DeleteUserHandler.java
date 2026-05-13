package com.projectLudoteca.ludoteca.command.deleteUser;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DeleteUserHandler {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public DeleteUserHandler(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public String handle(String userId, User authenticatedAdmin) {
        // Validar se o ID do usuário é válido
        UUID userIdUuid;
        try {
            userIdUuid = UUID.fromString(userId);
        } catch (RuntimeException e) {
            throw new BusinessException("USR_003", "Id de usuário inválido!");
        }

        // Validar se o usuário tenta deletar a si mesmo
        if (authenticatedAdmin.getId().equals(userIdUuid)) {
            throw new BusinessException("USR_004", "Não é possível deletar sua própria conta.");
        }

        // Buscar o usuário a ser deletado
        User userToDelete = userRepository.findUserNativeAndRemovedFalse(userIdUuid)
                .orElseThrow(() -> new BusinessException("USR_002", "Usuário não encontrado ou já foi removido."));

        // Validar se o usuário já foi removido
        if (userToDelete.getRemoved()) {
            throw new BusinessException("USR_005", "Este usuário já foi removido.");
        }

        // Validar se o usuário possui empréstimos ativos
        if (loanRepository.hasActiveLoansByUserId(userIdUuid)) {
            throw new BusinessException("USR_006", "Não é possível remover um usuário com empréstimos ativos.");
        }

        // Realizar exclusão lógica
        userToDelete.setRemoved(true);
        userToDelete.setDeletedAt(LocalDateTime.now());

        userRepository.save(userToDelete);

        return "Usuário removido com sucesso!";
    }
}
