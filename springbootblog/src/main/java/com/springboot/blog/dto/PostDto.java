package com.springboot.blog.dto;


import com.springboot.blog.model.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min=2, message = "Post title should have at least 2 character")
    private String title;

    @NotEmpty
    @Size(min = 2, message = "Post description should have at least 2 character")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;


}









