package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;


    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }
    //http://localhost:8080/api/v1/review?propertyId=1
    @PostMapping
    public  ResponseEntity<?> writes(
            @RequestBody Review review,
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user

            ){
        Property property = propertyRepository.findById(propertyId).get();
        review.setProperty(property);
        review.setAppUser(user);
        Review save = reviewRepository.save(review);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

}
