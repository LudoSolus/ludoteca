package com.projectLudoteca.ludoteca.query.detailsUser;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminView;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GetUserDetailsHandler {

    public GetUserDetailsView handle(User user) {

        return new GetUserDetailsView(user.getPublicId(),
                user.getName(),
                user.getCpf(),
                user.getEducationalInstitution() != null
                        ? user.getEducationalInstitution().getId().toString()
                        : null,
                user.getPhone(),
                user.getRa() != null
                        ? user.getRa()
                        : null,
                user.getEmail(),
                user.getBirthDate());
    }

}
