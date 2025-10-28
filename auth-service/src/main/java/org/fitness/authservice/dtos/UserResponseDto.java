package org.fitness.authservice.dtos;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String keycloakId;
    private String email ;
    private String firstName;
    private String lastName;
    private String createdAt;
    private String updatedAt;

}
