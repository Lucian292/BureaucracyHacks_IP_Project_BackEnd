package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.models.UserJPA;
import com.bureaucracyhacks.refactorip.services.AuthenticationService;
import com.bureaucracyhacks.refactorip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestParam String usernameOrEmail, @RequestParam String password) {
        return ResponseEntity.ok(authenticationService.authenticateUser(usernameOrEmail, password));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email, @RequestParam String username, @RequestParam String newPassword)
    {
        try{
            userService.forgotPassword(email, username, newPassword);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Email or password is incorrect!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String name, @RequestParam String surname, @RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String phone_number, @RequestParam String city) throws IOException, IOException {
        if (userService.isUsernameTaken(username)) {
            return new ResponseEntity<>("Username '" + username + "' is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userService.isEmailTaken(email)) {
            return new ResponseEntity<>("There already exists an user with the email '" + email + "'!", HttpStatus.BAD_REQUEST);
        }

        if (!userService.isValidName(name)) {
            return new ResponseEntity<>("Name '" + name + "' is not valid!", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidSurname(surname)) {
            return new ResponseEntity<>("Surname '" + surname + "' is not valid!", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidUsername(username)) {
            return new ResponseEntity<>("Username '" + username + "' is not valid! (It should only contain letters, numbers and '_' and be at least 6 characters long.)", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidEmail(email)) {
            return new ResponseEntity<>("Email '" + email + "' is not valid!", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidPassword(password)) {
            return new ResponseEntity<>("Password is not valid! (It should contain at least one upper case, one lower case, one number and be at least 8 characters long.)", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidPhoneNumber(phone_number)) {
            return new ResponseEntity<>("Phone number '" + phone_number + "' is not valid!", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidCity(city)) {
            return new ResponseEntity<>("City '" + city + "' does not exist in Romania!", HttpStatus.BAD_REQUEST);
        }

        UserJPA user = new UserJPA();
        user.setName(name);
        //var jwtToken = tokenService.generateToken(user);
        userService.registerUser(name, surname, username, email, password, phone_number, city);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
