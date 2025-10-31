package com.projectLudoteca.ludoteca.query.listAllUsers;

public record GetAllUsersView(String publicId,
                              String name,
                              String cpf,
                              String email,
                              String ra,
                              String birthDate){
}
