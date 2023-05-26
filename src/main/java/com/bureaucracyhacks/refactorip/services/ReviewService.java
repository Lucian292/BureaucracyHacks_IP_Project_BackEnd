package com.bureaucracyhacks.refactorip.services;

import com.bureaucracyhacks.refactorip.models.ReviewJPA;
import com.bureaucracyhacks.refactorip.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview (@RequestParam String comment, @RequestParam Integer rating, @RequestParam Long user_id, @RequestParam Long institution_id) {
        ReviewJPA reviewJPA = new ReviewJPA();
        reviewJPA.setComment(comment);
        reviewJPA.setRating(rating);
        reviewJPA.setUser_id(user_id);
        reviewJPA.setInstitution_id(institution_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        reviewJPA.setCreated_at(sdf.format(now));

        reviewRepository.save(reviewJPA);
    }
}

