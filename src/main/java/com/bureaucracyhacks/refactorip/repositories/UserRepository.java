package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.RoleJPA;
import com.bureaucracyhacks.refactorip.models.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserJPA, Long> {
    Optional<UserJPA> findByUsername(String username);
    Optional<UserJPA> findByEmail(String email);
    Optional<UserJPA> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username); //its never used
    Boolean existsByEmail(String email); //its never used
    List<UserJPA> findAllByRole(RoleJPA role);
}
