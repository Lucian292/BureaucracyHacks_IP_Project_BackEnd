package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.RoleJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleJPA, Long> {
    Optional<RoleJPA> findByName(String name);

}