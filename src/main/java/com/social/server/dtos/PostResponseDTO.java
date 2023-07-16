package com.social.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    //post
    PostDTO newPost;
    //images of the post
    List<ImageDTO> postImages;
    //tagged users of the post
    List<PostTaggedUserDTO> postTaggedUsers;
    //comments of the post
    List<CommentDTO> comments;


}
