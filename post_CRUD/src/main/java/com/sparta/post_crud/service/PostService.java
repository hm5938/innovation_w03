package com.sparta.post_crud.service;

import com.sparta.post_crud.dto.PostRequestDto;
import com.sparta.post_crud.dto.ResponseDto;
import com.sparta.post_crud.dto.PasswordDto;
import com.sparta.post_crud.entity.Post;
import com.sparta.post_crud.repository.PostRepository;
import com.sparta.post_crud.repository.ReviewRepository;
import com.sparta.post_crud.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final String STR_NULL = "post id isn't exist";

    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto , String username) {
        Post post = new Post(requestDto ,username );

        postRepository.save(post);

        return ResponseDto.success(post);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", STR_NULL);
        }

        return ResponseDto.success(optionalPost.get());
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost() {
        return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto, String username) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NULL_POST_ID", STR_NULL);
        }

        Post post = optionalPost.get();

        if(!post.getName().equals(username)){
            return ResponseDto.fail("NOT_POSTING_USER","????????? ???????????? ????????????.");
        }
        post.update(requestDto ,username);
        return ResponseDto.success(post);
    }

    @Transactional
    public ResponseDto<?> deletePost(Long id,String username) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", STR_NULL);
        }

        Post post = optionalPost.get();

        if(!post.getName().equals(username)){
            return ResponseDto.fail("NOT_POSTING_USER","????????? ???????????? ????????????.");
        }

        reviewRepository.deleteAllByPost(post.getId());
        postRepository.delete(post);

        return ResponseDto.success(true);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> validateAuthorByPassword(Long id, PasswordDto password) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", STR_NULL);
        }

        Post post = optionalPost.get();

        if (!post.getPassword().equals(password.getPassword())) {
            return ResponseDto.fail("PASSWORD_NOT_CORRECT", "password is not correct");
        }

        return ResponseDto.success(true);
    }

//    public Long update(Long id, PostRequestDto postRequestDto){
//        Post post1 = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("?????? ???????????? ???????????? ????????????."));
//        post1.update(postRequestDto);
//        return post1.getId();
//    }
//
//    public Post read(Long id){
//        Post post1= postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
//        return post1;
//    }
//
//    public boolean checkPassword(Long id, String password){
//        Post post1 = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
//        return post1.getPassword().equals(password);
//    }
//
//    public Long delete(Long id){
//        postRepository.deleteById(id);
//        return id;
//    }
//
//    public Post createPost(PostRequestDto postRequestDto){
//        Post post = new Post(postRequestDto);
//        return postRepository.save(post);
//    }
//
//
//    public List<PostResponseDto> getList(){
//        List<Post> posts= postRepository.findAll(Sort.by("createdAt"));
//        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
//        for (Post post:posts) {
//            PostResponseDto postResponseDto = new PostResponseDto(post);
//            postResponseDtoList.add(postResponseDto);
//        }
//
//
//        return postResponseDtoList;
//    }
//
//    //?????? ???????????? ???????????? ?????????
//    public <T> List<T> findallby(Class<T> type){
//        List<T> list = null;
//
//        return list;
//    }

}
