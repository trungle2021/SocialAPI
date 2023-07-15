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
    List<ImageDTO> imagesToUpdate;
    List<ImageDTO> imagesToDelete;
    List<PostTaggedUserDTO> postTaggedUsers;
}
