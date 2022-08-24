package com.sparta.post_crud.controller;


import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.ReviewRequestDto;
import com.sparta.post_crud.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{id}")
    public ResponseDto<?> getReviews(@PathVariable Long post){ return reviewService.getAllReviews(post);}


//    @GetMapping("/api/reviews/{id}")
//    public ResponseDto<?> getReview(@PathVariable Long id){ return reviewService.getReview(id); }

    @PostMapping("api/reviews")
    public ResponseDto<?> createReview(@RequestBody ReviewRequestDto postRequestDto){
        return reviewService.createReview(postRequestDto);
    }

    @DeleteMapping("api/reviews/{id}")
    public ResponseDto<?> deleteReview(@PathVariable Long id){
        return reviewService.deleteReview(id);
    }

    @PutMapping("api/reviews/{id}")
    public ResponseDto<?> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDto postRequestDto){
        return reviewService.updateReview(id,postRequestDto);
    }

}
