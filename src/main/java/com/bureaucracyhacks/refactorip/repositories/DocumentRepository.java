package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.DocumentJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentJPA, Integer> {
    Optional<DocumentJPA> findByName(String name);
    Boolean existsByName(String name);
    Boolean existsById(int institution_id);
}
