package org.fitness.authservice.mappers;

import org.fitness.authservice.dtos.UserRequestDto;
import org.fitness.authservice.dtos.UserResponseDto;
import org.fitness.authservice.models.User;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
// This class is intended to map data transfer objects (DTOs) to model entities and vice versa.


    //to model
    public User toModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        return user;
    }

    //to response dto
    public UserResponseDto toDTO(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setKeycloakId(user.getKeycloakId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setCreatedAt(String.valueOf(user.getCreatedAt()));
        userResponseDto.setUpdatedAt(String.valueOf(user.getUpdatedAt()));
        return userResponseDto;
    }
}
