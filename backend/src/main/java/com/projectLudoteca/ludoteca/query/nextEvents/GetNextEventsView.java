package com.projectLudoteca.ludoteca.query.nextEvents;

import java.time.LocalDateTime;

public record GetNextEventsView(String name,
                                LocalDateTime startDate,
                                String finalDate,
                                String street,
                                String number,
                                String neighborhood,
                                String city,
                                String state,
                                String zipCode) {
}
