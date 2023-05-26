package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

       @PostMapping("/findByName")
        public ResponseEntity<?> findByName(@RequestParam String name) {

             if(documentService.findByName(name).getBody()!= Optional.empty())
                {
                    return new ResponseEntity<>(documentService.findByName(name), HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity<>("Document does not exist!", HttpStatus.BAD_REQUEST);
                }
        }
}
