package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.ReviewJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<ReviewJPA, Long> {
    Optional<ReviewJPA> findByRating(Integer rating);
}

