package com.springboot.blog.serviceinterfase;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.model.Comment;

import java.util.List;

public interface CommentService {

    //Create Comment
    public CommentDto createComment(Long postId, CommentDto commentDto);

    //Get All Comment  post by id
    List<CommentDto> getCommentByPostId(Long postId);

    //get comment by id and post by id
    public CommentDto getCommentById(Long postId, Long commentId);

    //update comment
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    //delete comment by id
    public void deleteComment(Long postId, Long commentId);

}
