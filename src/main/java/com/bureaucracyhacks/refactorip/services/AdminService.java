package com.bureaucracyhacks.refactorip.services;

import com.bureaucracyhacks.refactorip.exceptions.UserNotFoundException;
import com.bureaucracyhacks.refactorip.models.RoleJPA;
import com.bureaucracyhacks.refactorip.models.UserJPA;
import com.bureaucracyhacks.refactorip.repositories.RoleRepository;
import com.bureaucracyhacks.refactorip.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public void makeAdmin(String name) {
        UserJPA user;
        try {
            user = userRepository.findByUsername(name).orElseThrow();
        }
        catch(NoSuchElementException e)
        {
            throw new UserNotFoundException();
        }
        RoleJPA role = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        user.setRole(role);
        userRepository.save(user);
    }

    public void removeAdmin(String username) {
        UserJPA user;
        try {
            user = userRepository.findByUsername(username).orElseThrow();
        }
        catch(NoSuchElementException e)
        {
            throw new UserNotFoundException();
        }
        RoleJPA role = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.setRole(role);
        userRepository.save(user);
    }

    public List<String> getAllAdmins() {
        RoleJPA role = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        List<UserJPA> admins = userRepository.findAllByRole(role);
        return admins.stream().map(UserJPA::getUsername).toList();
    }
}
