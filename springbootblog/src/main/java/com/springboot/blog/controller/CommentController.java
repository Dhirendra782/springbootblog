package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.serviceinterfase.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        CommentDto saveComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(saveComment, HttpStatus.CREATED);
    }

    //get comment by id and post by id All List
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentPostById(@PathVariable("postId") Long postId) {
        List<CommentDto> getAllComment = commentService.getCommentByPostId(postId);
        return new ResponseEntity<>(getAllComment,HttpStatus.OK);
    }


    //get comment by id and post by id
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("postId") Long postId,
                                                 @PathVariable("id") Long commentId) {
        CommentDto commentById = commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentById,HttpStatus.OK);
    }

    //update comment by id and post by id
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto saveComment = commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(saveComment,HttpStatus.OK);

    }

    //Delete comment by id
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
                                                @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Successfully:", HttpStatus.OK);
    }

}