package com.social.server.services;

import com.social.server.dtos.PostDTO;
import com.social.server.entities.PostImages;
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
     PostDTO createPost(Posts newPost, List<MultipartFile> files, List<PostTaggedUsers> postTaggedUsers);
     Posts insertPost(Posts newPost);
     PostDTO updatePost(Posts updatePost, List<MultipartFile> images, List<PostTaggedUsers> postTaggedUsers);
     Posts editPost(Posts updatePost);
     boolean deletePost(String postId);
}
