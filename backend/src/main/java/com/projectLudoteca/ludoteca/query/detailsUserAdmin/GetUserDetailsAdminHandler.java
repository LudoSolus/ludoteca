package com.projectLudoteca.ludoteca.query.detailsUserAdmin;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GetUserDetailsAdminHandler {

    private final UserRepository userRepository;

    public GetUserDetailsAdminHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GetUserDetailsAdminView handle(String id) {

        if (id == null) {
            throw new IllegalArgumentException("O id do usuário não pode ser nulo.");
        }

        UUID userId;

        try{
            userId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de usuário inválido!");
        }

        User user = userRepository.findUserNativeAndRemovedFalse(userId).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));

        return new GetUserDetailsAdminView(user.getId(),
                        user.getPublicId(),
                        user.getName(),
                        user.getEducationalInstitution() != null
                                ? user.getEducationalInstitution().getInstitutionName()
                                : "N/A",
                        user.getPhone(),
                        user.getRa() != null
                                ? user.getRa()
                                : "N/A",
                        user.getEmail(),
                        getAgeCategory(user.getBirthDate()),
                        user.getUserRole().name(),
                        user.getMinBoardGames() != 0 ? (int) user.getMinBoardGames()/60 : 0,
                        user.getMinEscapeRoom() != 0 ? (int) user.getMinEscapeRoom()/60 : 0,
                        user.getMinRpg() != 0 ? (int) user.getMinRpg()/60 : 0);
    }

    private static String getAgeCategory(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("A data de nascimento não pode ser nula.");
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();

        if (age >= 18) {
            return "+18";
        } else if (age >= 16) {
            return "+16";
        } else if (age >= 14) {
            return "+14";
        } else if (age >= 12) {
            return "+12";
        } else {
            return "Infantil";
        }
    }

}
