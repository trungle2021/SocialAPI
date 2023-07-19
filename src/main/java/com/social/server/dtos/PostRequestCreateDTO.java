package com.social.server.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestCreateDTO {
     @NotEmpty(message = "Post cannot be null")
     PostDTO newPost;
     List<ImageDTO> postImages;
     List<PostTaggedUserDTO> postTaggedUsers;
     List<CommentDTO> comments;
}
