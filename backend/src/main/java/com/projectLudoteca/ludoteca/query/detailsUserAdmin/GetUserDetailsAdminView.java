package com.projectLudoteca.ludoteca.query.detailsUserAdmin;

import java.util.UUID;

public record GetUserDetailsAdminView(UUID id,
                                    String publicId,
                                    String name,
                                    String institution,
                                    String phone,
                                    String ra,
                                    String email,
                                    String ageCategory,
                                    String userType
) {
}
