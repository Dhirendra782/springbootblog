package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

import com.springboot.blog.serviceinterfase.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    //create post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto savePost = postService.createPost(postDto);
        return new ResponseEntity<>(savePost, HttpStatus.CREATED);
    }

    //get post list
    @GetMapping
    public ResponseEntity<PostResponse> getPostList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        PostResponse getList = postService.getPostList(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(getList,HttpStatus.OK);

    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto getPostId = postService.getPostById(id);
        return new ResponseEntity<>(getPostId,HttpStatus.OK);
    }

    //update post
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto){
         postService.updatePost(id,postDto);
        return new ResponseEntity<>("Post Updated Successfully ",HttpStatus.OK);
    }

    //delete post
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        boolean delete = postService.deletePost(id);
        if (delete) {
            return new ResponseEntity<>("Post deleted Succesfully ",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Post Not Found ",HttpStatus.NOT_FOUND);
        }

    }

}
