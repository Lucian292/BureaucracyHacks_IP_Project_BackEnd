package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.TaskJPA;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TaskRepository extends JpaRepository<TaskJPA, Long> {
    Optional<TaskJPA> findByName(String name);
    @NotNull
    Optional<TaskJPA> findById(@NotNull Long id);
}
