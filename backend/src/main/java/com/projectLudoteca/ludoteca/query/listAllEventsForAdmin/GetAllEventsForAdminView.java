package com.projectLudoteca.ludoteca.query.listAllEventsForAdmin;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetAllEventsForAdminView(UUID id,
                                       String name,
                                       LocalDateTime startDate,
                                       String finalDate,
                                       String street,
                                       String number,
                                       String neighborhood,
                                       String city,
                                       String state,
                                       String zipCode) {
}
