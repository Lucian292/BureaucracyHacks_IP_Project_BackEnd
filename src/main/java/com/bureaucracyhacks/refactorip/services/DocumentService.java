package com.bureaucracyhacks.refactorip.services;

import com.bureaucracyhacks.refactorip.exceptions.DocumentNotFoundException;
import com.bureaucracyhacks.refactorip.models.DocumentJPA;
import com.bureaucracyhacks.refactorip.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void makeDocument(String name)
    {
        DocumentJPA document;
        try {
            document = documentRepository.findByName(name).orElseThrow();
        }
        catch(NoSuchElementException e)
        {
            throw new DocumentNotFoundException();
        }
       // documentRepository.save(document);
    }

    public void removeDocument(String name)
    {
        DocumentJPA document;
        try {
            document = documentRepository.findByName(name).orElseThrow();
        }
        catch(NoSuchElementException e)
        {
            throw new DocumentNotFoundException();
        }
        //documentRepository.delete(document);
    }

    public ResponseEntity<?> findByName(String name) {

            return ResponseEntity.ok(documentRepository.findByName(name));
    }


}
