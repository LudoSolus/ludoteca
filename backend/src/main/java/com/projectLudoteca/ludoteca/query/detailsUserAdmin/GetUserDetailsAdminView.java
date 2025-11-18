package com.projectLudoteca.ludoteca.query.detailsUserAdmin;

import java.util.UUID;

public record GetUserDetailsAdminView(UUID id,
                                    String publicId,
                                    String institution,
                                    String phone,
                                    String ra,
                                    String email,
                                    String ageCategory,
                                    String userType
) {
}
