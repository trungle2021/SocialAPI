package com.social.server.services.Post;

import com.social.server.entities.PostImages;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
     List<Posts> getPostsByUserId(String userId);


     Page<Posts> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field);

     List<Posts> getPostsByUserIdWithSorting(String userId, String field);
     Posts getPostByPostId(String id);
     Posts createPost(Posts newPost, List<PostImages> images,List<PostTaggedUsers> postTaggedUsers);
     Posts insertPost(Posts newPost, List<PostImages> images);
     Posts updatePost(Posts updatePost, List<PostImages> images);
     boolean deletePost(String postId);
}
