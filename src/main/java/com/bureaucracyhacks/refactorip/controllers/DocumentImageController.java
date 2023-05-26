package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.exceptions.ImageDimensionException;
import com.bureaucracyhacks.refactorip.exceptions.ImageFormatException;
import com.bureaucracyhacks.refactorip.exceptions.ImageNotFoundException;
import com.bureaucracyhacks.refactorip.exceptions.UserNotFoundException;
import com.bureaucracyhacks.refactorip.services.DocumentImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class DocumentImageController {

    private final DocumentImageService documentImageService;


    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file, int id_user) throws IOException {

        try{
        documentImageService.uploadImage(file, id_user);
        } catch (ImageFormatException e)
        {
            return new ResponseEntity<>("Invalid format! Try JPG, JPEG OR PNG!", HttpStatus.BAD_REQUEST);
        }
        catch (ImageDimensionException e)
        {
            return new ResponseEntity<>("Invalid dimensions, maximum size is 10MB!", HttpStatus.BAD_REQUEST);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Image uploaded successfully!", HttpStatus.OK);
    }

    @GetMapping("/download/{imageName}")
    public ResponseEntity<?> downloadImage(@PathVariable String imageName, int id_user) {

        byte[] image;
        try {
            image = documentImageService.downloadImage(imageName, id_user);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        catch (ImageNotFoundException e)
        {
            return new ResponseEntity<>("Image not found!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

    @GetMapping("getAllImages/{id_user}")
    public ResponseEntity<?> getAllImages(@PathVariable int id_user) {
        try {
            return new ResponseEntity<>(documentImageService.getAllImages(id_user), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
    }
}
