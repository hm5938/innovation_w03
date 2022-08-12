package com.sparta.post_crud.repository;

import com.sparta.post_crud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}