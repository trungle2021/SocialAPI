package com.social.server.dtos;

import com.social.server.entities.PostImages;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    Posts newPost;
    List<PostImageDTO> postImages;
    List<PostTaggedUsers> postTaggedUsers;
    //list of comments
    //list of images of comments
}
