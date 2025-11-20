package com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin;

import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class GetAllUsersForAdminHandler {

    private final UserRepository repository;

    public GetAllUsersForAdminHandler(UserRepository repository) {
        this.repository = repository;
    }

    public List<GetAllUsersForAdminView> handle() {

        return repository.findAll()
                         .stream()
                         .map(u -> new GetAllUsersForAdminView(u.getId(), u.getEmail(), getAgeCategory(u.getBirthDate()), u.getUserRole().name()))
                         .toList();
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
