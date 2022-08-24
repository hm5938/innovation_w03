package com.sparta.post_crud.repository;

import com.sparta.post_crud.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByPostOrderByModifiedAtDesc(Long post);
}
