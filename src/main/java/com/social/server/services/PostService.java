package com.social.server.services;

import com.social.server.dtos.PostDTO;
import com.social.server.dtos.PostRequestCreateDTO;
import com.social.server.dtos.PostRequestUpdateDTO;
import com.social.server.dtos.PostResponseDTO;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
     List<Posts> getPostsByUserId(String userId);


     Page<Posts> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field);

     List<Posts> getPostsByUserIdWithSorting(String userId, String field);
     Posts getPostById(String userId, String postId);
     PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO);
     PostDTO insertPost(PostDTO newPost);
     PostResponseDTO updatePost(PostRequestUpdateDTO postRequestUpdateDTO);
     PostDTO editPost(PostDTO updatePost);
     boolean deletePost(String postId);
}
