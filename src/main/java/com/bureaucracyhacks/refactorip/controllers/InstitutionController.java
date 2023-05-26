package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.exceptions.InstitutionNotFoundException;
import com.bureaucracyhacks.refactorip.models.InstitutionJPA;
import com.bureaucracyhacks.refactorip.repositories.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {
    private final InstitutionRepository institutionRepository;

    @GetMapping("/{name}")
    public InstitutionJPA getInstitutionByName(@PathVariable String name) {
        return institutionRepository.findByName(name)
                .orElseThrow(() -> new InstitutionNotFoundException("Institution not found with name: " + name));
    }
}
