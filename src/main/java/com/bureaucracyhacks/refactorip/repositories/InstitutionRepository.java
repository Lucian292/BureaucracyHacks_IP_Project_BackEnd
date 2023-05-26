package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.InstitutionJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionJPA, Long> {
    //find by id
    Optional<InstitutionJPA> findByInstitutionId(Long institutionId);
    Optional<InstitutionJPA> findByName(String name);
}
