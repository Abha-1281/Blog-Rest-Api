package com.springboot.restapi.springbootrestapiblog.service.impl;

import com.springboot.restapi.springbootrestapiblog.entity.Post;
import com.springboot.restapi.springbootrestapiblog.exception.ResourceNotFoundException;
import com.springboot.restapi.springbootrestapiblog.payload.PostDto;
import com.springboot.restapi.springbootrestapiblog.payload.PostResponse;
import com.springboot.restapi.springbootrestapiblog.repository.PostRepository;
import com.springboot.restapi.springbootrestapiblog.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postRequestDto) {

        // convert request DTO to entity
        Post postEntity = convertDtoToEntity(postRequestDto);

        Post savedPostEntity = postRepository.save(postEntity);

        PostDto postResponseDto = convertEntityToDto(savedPostEntity);

        return postResponseDto;

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {


        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = postPage.getContent();

        List<Post> lstpostResponseEntity = postRepository.findAll();

        List<PostDto> lstpostResponseDto = listOfPosts.stream().map(postEntity -> convertEntityToDto(postEntity)).collect(Collectors.toList());


        PostResponse postResponse = new PostResponse();
        postResponse.setContent(lstpostResponseDto);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLast(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        PostDto getPostByIdResponseBody = convertEntityToDto(postEntity);
        return getPostByIdResponseBody;

    }

    @Override
    public PostDto updatePost(PostDto requestBody, long id) {
        //get post by id and if it exist return response

        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postEntity.setTitle(requestBody.getTitle());
        postEntity.setDescription(requestBody.getDescription());
        postEntity.setContent(requestBody.getContent());

        Post updatedPostEntity = postRepository.save(postEntity);
        PostDto updatePostResponseBody = convertEntityToDto(updatedPostEntity);
        return updatePostResponseBody;
    }

    @Override
    public void deletePost(long id) {
        Post postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(postEntity);
    }

    private Post convertDtoToEntity(PostDto postDto){
        Post postEntity = new Post();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setDescription(postDto.getDescription());
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }


    private PostDto convertEntityToDto(Post postEntity){
        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        postDto.setDescription(postEntity.getDescription());
        postDto.setContent(postEntity.getContent());
        return postDto;
    }


}
