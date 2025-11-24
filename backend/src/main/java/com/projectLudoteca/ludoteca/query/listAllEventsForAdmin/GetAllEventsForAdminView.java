package com.projectLudoteca.ludoteca.query.listAllEventsForAdmin;

import java.time.LocalDateTime;

public record GetAllEventsForAdminView(String name,
                                       LocalDateTime startDate,
                                       String finalDate,
                                       String street,
                                       String number,
                                       String neighborhood,
                                       String city,
                                       String state,
                                       String zipCode) {
}
