package com.springboot.restapi.springbootrestapiblog.controller;

import com.springboot.restapi.springbootrestapiblog.entity.Post;
import com.springboot.restapi.springbootrestapiblog.payload.PostDto;
import com.springboot.restapi.springbootrestapiblog.payload.PostResponse;
import com.springboot.restapi.springbootrestapiblog.service.PostService;
import com.springboot.restapi.springbootrestapiblog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //@Controller+ @ResponseBody
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postRequestDto){
        PostDto postResponseBody = postService.createPost(postRequestDto);
        return new ResponseEntity<>(postResponseBody, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        PostResponse getAllPostResponseBody = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getAllPostResponseBody, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        PostDto getPostByIdResponseBody = postService.getPostById(id);
        return new ResponseEntity<>(getPostByIdResponseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto updatePostRequestBody, @PathVariable(name = "id") long id){
        PostDto updatePostResponseBody = postService.updatePost(updatePostRequestBody,id);
        return new ResponseEntity<>(updatePostResponseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted Successfully", HttpStatus.OK);
    }
}
