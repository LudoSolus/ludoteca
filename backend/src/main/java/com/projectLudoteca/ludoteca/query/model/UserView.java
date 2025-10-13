package com.projectLudoteca.ludoteca.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserView {
    private String publicId;
    private String name;
    private String cpf;
    private String email;
    private String ra;
    private String birthDate;
}
