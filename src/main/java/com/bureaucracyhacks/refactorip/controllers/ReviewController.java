package com.bureaucracyhacks.refactorip.controllers;

import com.bureaucracyhacks.refactorip.models.ReviewJPA;
import com.bureaucracyhacks.refactorip.repositories.ReviewRepository;
import com.bureaucracyhacks.refactorip.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviewInstitution")
    public ResponseEntity<String> sendReview (@RequestParam String comment, @RequestParam Integer rating, @RequestParam Long user_id, @RequestParam Long institution_id) {

        try {
            reviewService.addReview(comment, rating, user_id, institution_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("Review has been posted successfully");
    }

//    To be implemented
//    @PostMapping("/reviewApp")
//    public ResponseEntity<String> sendReview (@RequestParam String comment, @RequestParam Integer rating, @RequestParam Long user_id) {
//        ReviewJPA reviewJPA = new ReviewJPA();
//        reviewJPA.setComment(comment);
//        reviewJPA.setRating(rating);
//        reviewJPA.setUser_id(user_id);
//        reviewJPA.setInstitution_id(institution_id);
////       reviewJPA.setCreated_at(reviewRequest.getCreated_at());
////       reviewJPA.setFeedback_id(reviewRequest.getFeedback_id());
////       reviewJPA.setInstitution_id(reviewRequest.getInstitution_id());
//        String text = "2011-10-02 18:48:05.123456";
//        Timestamp ts = Timestamp.valueOf(text);
//        reviewJPA.setCreated_at(ts.toString());
//
//        reviewRepository.save(reviewJPA);
//
//        return ResponseEntity.ok("review have beed posted succesfully");
//    }
}
