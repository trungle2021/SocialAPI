package com.social.server.services;

import com.social.server.dtos.*;
import com.social.server.entities.Post.Posts;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
     //this method will retrieve list of posts
     Page<PostResponseDTO> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field);
     PostResponseDTO getPostByPostId(String postParentId,int offsetComment, int limitComment);

     Posts findPostById(String postId);
     PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO);
     PostDTO insertPost(PostDTO newPost);
     PostDTO sharePost(PostDTO sharePost);
     PostResponseDTO updatePost(PostRequestUpdateDTO postRequestUpdateDTO);
     PostDTO editPost(PostDTO updatePostDTO);

     Page<PostResponseDTO> getNewsFeed(String userId, int offset, int limit);
}
