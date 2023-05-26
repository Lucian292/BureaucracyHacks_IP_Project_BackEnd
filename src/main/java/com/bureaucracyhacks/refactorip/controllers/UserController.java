package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.exceptions.DocumentNotFoundException;
import com.bureaucracyhacks.refactorip.exceptions.TaskNotFoundException;
import com.bureaucracyhacks.refactorip.exceptions.UserNotFoundException;
import com.bureaucracyhacks.refactorip.services.AuthenticationService;
import com.bureaucracyhacks.refactorip.services.UserService;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-service")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String username, @RequestParam String email, @RequestParam String phone_number, @RequestParam String password, @RequestParam String name, @RequestParam String surname, @RequestParam String city) {

        try {
            userService.updateUser(username, email, phone_number, password, name, surname, city);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User modified successfully!", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {

        try {
            userService.deleteUser(username);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/verifyPassword")
    public ResponseEntity<?> verifyPassword(@RequestParam String username, @RequestParam String password) {
        System.out.println("verifyPassword");
        try {
            if (userService.verifyPassword(username, password)) {
                return new ResponseEntity<>("Password is valid!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Password is not valid!", HttpStatus.OK);
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.OK);
        }
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam String username, @RequestParam String password) {
        if (!userService.isValidPassword(password)) {
            return new ResponseEntity<>("Password is not valid! (It should contain at least one upper case, one lower case, one number and be at least 8 characters long.)", HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updatePassword(username, password);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }
    @PostMapping("/addDocument")
    public ResponseEntity<?> addDocument(@RequestParam String username, @RequestParam String documentName) {

        try {
            userService.addDocument(username, documentName);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        } catch (DocumentNotFoundException e) {
            return new ResponseEntity<>("Document not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Document added successfully!", HttpStatus.OK);
    }

    @GetMapping("/todo-list/{userTaskName}")
    public ResponseEntity<?> generateTodoList(@PathVariable String userTaskName, @RequestParam String username) {
        List<Map<String, String>> documentsAndInstitutionLocation;
        /* JSON format:
        * isDone:
        * DocName:
        * InstitutionLocation:
        */
        try {
            //generate the to do list based on the task and the user who requested it
            documentsAndInstitutionLocation = userService.generateTodoList(userTaskName, username);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(documentsAndInstitutionLocation);
    }

    @PostMapping("/updateDocument")
    public ResponseEntity<?> updateDocument(@RequestParam String userTaskName, @RequestParam String documentName, @RequestParam String status, @RequestParam String username)
    {
        try
        {
            //update the status of the document
            /* ONLY "pending" -> "done" ON STATUS */
            userService.updateDocument(documentName, status, username,userTaskName);
        } catch (DocumentNotFoundException e)
        {
            return new ResponseEntity<>("Document not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Document updated successfully!", HttpStatus.OK);
    }

    @PostMapping("/add-user-task")
    public ResponseEntity<?> addUserTask(@RequestParam String username, @RequestParam String taskName) {
        try {
            userService.addUserTask(username, taskName);
            userService.addUserDocumentsFromTask(username, taskName);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User task added successfully!", HttpStatus.OK);
    }

    @GetMapping("/get-user-tasks")
    public ResponseEntity<?> getUserTasks(@RequestParam String username) {
        List<String> userTasks = new ArrayList<>();
        try {
            userTasks = userService.getUserTasks(username);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(userTasks);
    }

}
