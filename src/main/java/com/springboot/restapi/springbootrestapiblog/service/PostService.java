package com.springboot.restapi.springbootrestapiblog.service;

import com.springboot.restapi.springbootrestapiblog.payload.PostDto;
import com.springboot.restapi.springbootrestapiblog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);

    PostDto updatePost(PostDto requestBody, long id);

    void deletePost(long id);
}
