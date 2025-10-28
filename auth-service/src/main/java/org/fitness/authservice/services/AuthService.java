package org.fitness.authservice.services;

import lombok.extern.slf4j.Slf4j;
import org.fitness.authservice.dtos.UserResponseDto;
import org.fitness.authservice.exceptions.EmailExistException;
import org.fitness.authservice.exceptions.UserNotFoundException;
import org.fitness.authservice.mappers.ModelMapper;
import org.fitness.authservice.models.Event;
import org.fitness.authservice.models.User;
import org.fitness.authservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper ;

    public AuthService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Method to register a user
    public UserResponseDto registerUser() throws EmailExistException {
        // register user already handled by keycloak event consumer service
        // This method will only return a successful registered user response dto
        Optional<User> optionalUser = userRepository.findTopByOrderByCreatedAtDesc();
        // above findTopByOrderByCreatedAtDesc() gets the most recently created user
        // is not thread safe for concurrent registrations or environment but works for simple cases

        if (optionalUser.isEmpty()) {
            throw new EmailExistException("No users found. Registration might have failed.");
        }
        User registeredUser = optionalUser.get();
        //convert the saved user back to a DTO
        return modelMapper.toDTO(registeredUser);
    }
    //  But now register is done via keycloak event consumer service so this method is not actively used
    //  However, it can still be useful for testing or other purposes.
    //  And also returns userResponseDto after successful registered user creation.

    // Method to get user profile
    public UserResponseDto getUserProfile(String id) throws UserNotFoundException {
        // Logic to get user profile

        return userRepository.findByKeycloakId(id)
                .map(modelMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // Method to validate user by keycloak id which is the true identifier
    public Boolean validateUserById(String id) {
        return userRepository.existsByKeycloakId(id);
    }

    //Method to consume events from keycloak to sync the user creation at keycloak
    public void consumeEvent(Event event) throws EmailExistException {
        String type = event.getType() ;

        //if type = REGISTER then create a newUser and save all attributes with keycloakID
        switch (type) {
            case "REGISTER" -> {
                String email = event.getDetails().get("email");
                Optional<User> optionalUser = userRepository.findByEmail(email);

                if (optionalUser.isPresent()) {
                    User existingUser = optionalUser.get();
                    if (existingUser.getKeycloakId() == null) {
                        existingUser.setKeycloakId(event.getUserId());
                        userRepository.save(existingUser);
                        return;
                    }
                    throw new EmailExistException("Email already exists");
                }
                User newUser = new User();
                newUser.setKeycloakId(event.getUserId());
                newUser.setEmail(email);
                newUser.setFirstName(event.getDetails().get("first_name"));
                newUser.setLastName(event.getDetails().get("last_name"));
                userRepository.save(newUser);

                log.info("REGISTER event processed for userId: {}", event.getUserId());
            }
            //else if type = LOGIN , log for now
            case "LOGIN" -> {
                log.info(event.getType(), " event received for userId: ", event.getUserId());
            }

            //else if type = CODE_TO_TOKEN , log for now
            case "CODE_TO_TOKEN" ->{
                log.info(event.getType(), " event received for userId: ", event.getUserId());
            }
        }
    }
}
