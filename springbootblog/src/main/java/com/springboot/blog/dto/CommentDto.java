package com.springboot.blog.dto;

import com.springboot.blog.model.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email id not should empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 3,message = "Content body must be minimum 3 character")
    private String body;

}


















