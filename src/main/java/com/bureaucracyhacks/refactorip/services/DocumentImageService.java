package com.bureaucracyhacks.refactorip.services;

import java.io.IOException;
import java.util.Optional;


import com.bureaucracyhacks.refactorip.exceptions.ImageDimensionException;
import com.bureaucracyhacks.refactorip.exceptions.ImageFormatException;
import com.bureaucracyhacks.refactorip.exceptions.ImageNotFoundException;
import com.bureaucracyhacks.refactorip.exceptions.UserNotFoundException;
import com.bureaucracyhacks.refactorip.models.DocumentImageJPA;
import com.bureaucracyhacks.refactorip.repositories.ImageRepository;
import com.bureaucracyhacks.refactorip.repositories.UserRepository;
import com.bureaucracyhacks.refactorip.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    public DocumentImageJPA uploadImage(MultipartFile file, int id_user) throws IOException, ImageDimensionException, ImageFormatException {

        //if the file has more than 10mb, throw an exception
        if (file.getSize() > 10000000) {
            throw new ImageDimensionException();
        }

        //if the file is not an jpg, jpeg or png, throw an exception
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg") &&
            !file.getContentType().equals("image/png")) {
            throw new ImageFormatException();
        }

        if(!userRepository.existsById((long) id_user)) {
            throw new UserNotFoundException();
        }

        DocumentImageJPA documentImage = new DocumentImageJPA();
        documentImage.setName(file.getOriginalFilename());
        documentImage.setType(file.getContentType());
        documentImage.setImage(ImageUtil.compressImage(file.getBytes()));
        documentImage.setId_user(id_user);

        return imageRepository.save(documentImage);
    }

    public byte[] downloadImage(String imageName, int id_user){

        if(!userRepository.existsById((long) id_user)) {
            throw new UserNotFoundException();
        }

        Optional<DocumentImageJPA> image = imageRepository.findByNameOnUser(imageName, id_user);
        if (image.isEmpty()) {
            throw new ImageNotFoundException();
        }


        return ImageUtil.decompressImage(image.get().getImage());
    }

    public Iterable<DocumentImageJPA> getAllImages(int id_user) {
        if(!userRepository.existsById((long) id_user)) {
            throw new UserNotFoundException();
        }
        return imageRepository.findAllByUserId(id_user);
    }
}
