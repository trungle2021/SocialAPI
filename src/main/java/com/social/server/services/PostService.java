package com.social.server.services;

import com.social.server.dtos.PostDTO;
import com.social.server.dtos.PostRequestCreateDTO;
import com.social.server.dtos.PostRequestUpdateDTO;
import com.social.server.dtos.PostResponseDTO;
import com.social.server.entities.Post.Posts;
import org.springframework.data.domain.Page;

public interface PostService {
     Page<PostDTO> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field);

     Page<PostDTO> getPostsByUserIdWithSorting(String userId, String sortBy);
     Posts getPostById(String userId, String postId);
     PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO);
     PostDTO insertPost(PostDTO newPost);
     PostResponseDTO updatePost(PostRequestUpdateDTO postRequestUpdateDTO);
     PostDTO editPost(PostDTO updatePost);
     boolean deletePost(String postId);
}
