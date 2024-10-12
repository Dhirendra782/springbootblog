package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.serviceinterfase.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper mapper;


    //Create comment
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToCommentEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id",postId));
        //set post into Comment entity
        comment.setPost(post);

        Comment commentSave = commentRepository.save(comment);
        return mapToCommentDto(commentSave);

    }

    //Get All Comment  post by id
    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        //retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert list of comment entityes to list of comment dto's
        return comments.stream().map(comment -> mapToCommentDto(comment))
                .collect(Collectors.toList());
    }

    //Get Comment by id and post by id
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //Retrieve post by postId
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        //Retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));

       if(!comment.getPost().getId().equals(post.getId())) {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
       }
        return mapToCommentDto(comment);
    }

    //update comment
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        //Retrieve post by postid
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","id",postId));

        //Retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(comment.getBody());
        Comment saveComment = commentRepository.save(comment);

        return mapToCommentDto(saveComment);
    }

    //delete comment by id
    @Override
    public void deleteComment(Long postId, Long commentId) {
        //Retrieve post by postId
        Post post = postRepository.findById(postId).orElseThrow(()->
                                                    new ResourceNotFoundException("post","id",postId));

       Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                                    new ResourceNotFoundException("Comment","Id",commentId));

       if(!comment.getPost().getId().equals(post.getId())) {
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not blong");
       }

        commentRepository.deleteById(commentId);
    }

    //convert Comment Entity into CommentDto
    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
        return commentDto;
    }

    //convert CommentDto into Comment Entity
    public  Comment mapToCommentEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }

}
