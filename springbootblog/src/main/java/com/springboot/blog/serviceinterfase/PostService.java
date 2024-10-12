package com.springboot.blog.serviceinterfase;


import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.model.Post;

import java.util.List;

public interface PostService {

    //create post
    public PostDto createPost(PostDto postDto);


    //get post by id
    public PostDto getPostById(Long id);

    //get post all list
    //public List<PostDto> getPostList();

    //get post all list implementing pagenaction
    public PostResponse getPostList(int pageNo, int pageSize, String sortBy,String sortDir);


    //update post
    public boolean updatePost(Long id, PostDto updatePost);

    //delete by id
    public boolean deletePost(Long id);

}
