package com.projectLudoteca.ludoteca.query.detailsUser;

import java.time.LocalDate;

public record GetUserDetailsView(String publicId,
                                 String name,
                                 String cpf,
                                 String institutionId,
                                 String phone,
                                 String ra,
                                 String email,
                                 LocalDate birthDate) {
}
