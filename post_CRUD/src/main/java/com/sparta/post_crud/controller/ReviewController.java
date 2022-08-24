package com.sparta.post_crud.controller;


import com.sparta.post_crud.UserDetailsImpl;
import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.ReviewRequestDto;
import com.sparta.post_crud.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/api/reviews/{post}")
    public ResponseDto<?> getReviews(@PathVariable Long post){ return reviewService.getAllReviews(post);}

    @PostMapping("api/reviews")
    public ResponseDto<?> createReview(@RequestBody ReviewRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return reviewService.createReview(postRequestDto,username);
    }

    @DeleteMapping("api/reviews/{id}")
    public ResponseDto<?> deleteReview(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return reviewService.deleteReview(id, username);
    }

    @PutMapping("api/reviews/{id}")
    public ResponseDto<?> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUser().getUsername();
        return reviewService.updateReview(id,postRequestDto, username);
    }

}
