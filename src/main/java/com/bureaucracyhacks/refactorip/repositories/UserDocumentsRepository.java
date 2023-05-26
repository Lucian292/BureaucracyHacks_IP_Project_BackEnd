package com.bureaucracyhacks.refactorip.repositories;

import com.bureaucracyhacks.refactorip.models.UserDocumentsJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDocumentsRepository extends JpaRepository<UserDocumentsJPA, Integer> {
    /*USED ONLY FOR SAVING THE NEW STATUS FOR A SPECIFIC DOCUMENT*/
}
