package com.bureaucracyhacks.refactorip.repositories;

import java.util.Optional;

import com.bureaucracyhacks.refactorip.models.DocumentImageJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<DocumentImageJPA, Long>{
    Optional<DocumentImageJPA> findByName(String imageName);
    @Query(value="SELECT * FROM doc_image i WHERE i.name = ?1 AND i.id_user = ?2", nativeQuery = true)
    Optional<DocumentImageJPA> findByNameOnUser(String imageName, int idUser);

    @Query(value="SELECT * FROM doc_image i WHERE i.id_user = ?1", nativeQuery = true)
    Iterable<DocumentImageJPA> findAllByUserId(int idUser);
}
