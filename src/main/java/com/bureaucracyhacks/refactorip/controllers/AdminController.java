package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.services.AdminService;
import com.bureaucracyhacks.refactorip.services.UserService;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;
    
    @PostMapping("/make-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> makeAdmin(@RequestParam String username) {
        System.out.println("User is now an admin");
        if(userService.isAlreadyAdmin(username)){
            return new ResponseEntity<>("User is already an admin!", HttpStatus.BAD_REQUEST);
        }
        if (userService.isUsernameTaken(username)){
            adminService.makeAdmin(username);
            return new ResponseEntity<>("User is now admin!", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User does not exist!", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/remove-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeAdmin(@RequestParam String username) {
        System.out.println("User is no longer an admin");
        if (userService.isUsernameTaken(username)) {
            adminService.removeAdmin(username);
            return new ResponseEntity<>("User is no longer admin!", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User does not exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<String> getAllAdmins() {
        //System.out.println("Getting all admins");
        return adminService.getAllAdmins();
    }
}
