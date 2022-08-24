package com.sparta.post_crud.service;

import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.ReviewRequestDto;
import com.sparta.post_crud.entity.Review;
import com.sparta.post_crud.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final String STR_NULL = "review id isn't exist";
    private final ReviewRepository reviewRepository;

    @Transactional
    public ResponseDto<?> createReview(ReviewRequestDto requestDto) {

        Review Review = new Review(requestDto);

        reviewRepository.save(Review);

        return ResponseDto.success(Review);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getReview(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return ResponseDto.fail("NULL_Review_ID", STR_NULL);
        }

        return ResponseDto.success(optionalReview.get());
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllReviews(Long post) {
        return ResponseDto.success(reviewRepository.findAllByPostOrderByModifiedAtDesc(post));
    }

    @Transactional
    public ResponseDto<Review> updateReview(Long id, ReviewRequestDto requestDto) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return ResponseDto.fail("NULL_Review_ID", STR_NULL);
        }

        Review review = optionalReview.get();
        review.update(requestDto);

        return ResponseDto.success(review);
    }

    @Transactional
    public ResponseDto<?> deleteReview(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", STR_NULL);
        }

        Review review = optionalReview.get();

        reviewRepository.delete(review);

        return ResponseDto.success(true);
    }

}
