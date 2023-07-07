package com.social.server.dtos;

import com.social.server.entities.PostImages;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
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
    PostDTO newPost;
    List<PostImageDTO> postImages;
    List<PostTaggedUserDTO> postTaggedUsers;
}
