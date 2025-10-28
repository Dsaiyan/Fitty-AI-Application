package org.fitness.authservice.controller;

import jakarta.validation.Valid;
import org.fitness.authservice.dtos.UserRequestDto;
import org.fitness.authservice.dtos.UserResponseDto;
import org.fitness.authservice.exceptions.EmailExistException;
import org.fitness.authservice.exceptions.UserNotFoundException;
import org.fitness.authservice.services.AuthService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //register user
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser() throws EmailExistException {
        // Logic to register user
        return new ResponseEntity<>(authService.registerUser(),
                HttpStatusCode.valueOf(201));
    }

    //get user profile
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable("id") String id) throws UserNotFoundException {
        // Logic to get user profile
        return new ResponseEntity<>(authService.getUserProfile(id), HttpStatusCode.valueOf(200));
    }
    //validate user
    @GetMapping("/validate/{id}")
    public ResponseEntity<Boolean> validateUser(@PathVariable("id") String id) {
        // Logic to validate user
        return new ResponseEntity<>(authService.validateUserById(id), HttpStatusCode.valueOf(200));
    }
}
