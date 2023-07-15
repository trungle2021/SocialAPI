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
public class PostRequestCreateDTO {
     PostDTO newPost;
     List<ImageDTO> postImages;
     List<PostTaggedUserDTO> postTaggedUsers;
}
