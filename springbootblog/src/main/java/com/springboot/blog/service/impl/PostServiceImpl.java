package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.exception.ResourceNotFoundException;

import com.springboot.blog.model.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.serviceinterfase.PostService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    //create post
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savePost = postRepository.save(post);
        return mapToPostDto(savePost);
    }

    //get post list also using pagenaction
    @Override
    public PostResponse getPostList(int pageNo, int pageSize, String sortBy, String sortDir) {
        //create Pageable instance
        //Pageable pageable = PageRequest.of(pageNo,pageSize);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);
        //get content for page object
        List<Post> listOfPosts  = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post-> mapToPostDto(post))
                                                        .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
            postResponse.setContent(content);
            postResponse.setPageNo(posts.getNumber());
            postResponse.setPageSize(posts.getSize());
            postResponse.setTotalElements(posts.getTotalElements());
            postResponse.setTotalPages(posts.getTotalPages());
            postResponse.setLast(posts.isLast());
        return postResponse;
    }

    //get post by id
    @Override
    public PostDto getPostById(Long id) {
        Post postId = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post"," Id",id));
        return mapToPostDto(postId);
    }

    //update post
    @Override
    public boolean updatePost(Long id, PostDto updatePost) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post ","id",id));
            post.setTitle(updatePost.getTitle());
            post.setDescription(updatePost.getDescription());
            post.setContent(updatePost.getContent());
            postRepository.save(post);
        return true;
    }

    //delete post by id
    @Override
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }

    }

    //convert Post Entity into PostDto
    public PostDto mapToPostDto(Post post) {
        PostDto postDto = mapper.map(post,PostDto.class);
        return postDto;
    }

    //convert PostDto into Post Entity
    public Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto,Post.class);
        return post;
    }

}
