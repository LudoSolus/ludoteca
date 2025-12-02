package com.projectLudoteca.ludoteca.query.nextEvents;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetNextEventsView(UUID id,
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
