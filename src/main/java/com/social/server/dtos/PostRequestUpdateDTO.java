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
public class PostRequestUpdateDTO {
    PostDTO updatePost;
    List<PostImageDTO> postImagesToUpdate;
    List<PostImageDTO> postImagesToDelete;
    List<PostTaggedUserDTO> postTaggedUsers;
}
