package com.social.server.services.implement.Post;

import com.social.server.entities.PostImages;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.Post.PostImageService;
import com.social.server.services.Post.PostService;
import com.social.server.services.Post.PostTaggedUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostImageService postImageService;
    private final PostTaggedUserService postTaggedUserService;


    @Override
    public List<Posts> getPostsByUserId(String userId) {
        return postRepository.findAllPostsByUserId(userId);
    }

    @Override
    public Page<Posts> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field) {
        if(StringUtils.hasText(field)){
            return postRepository.findAll(PageRequest.of(offset,limit,Sort.by(Sort.Direction.ASC,field)));
        }else{
            return postRepository.findAll(PageRequest.of(offset,limit,Sort.by(Sort.Direction.ASC,"posted_at")));
        }
    }

    @Override
    public List<Posts> getPostsByUserIdWithSorting(String userId, String field) {
        return postRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    @Override
    public Posts getPostByPostId(String postId) {
        return postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Posts Not Found: " + postId));
    }

    @Override
    @Transactional
    public Posts createPost(Posts newPost, List<PostImages> images,List<PostTaggedUsers> postTaggedUsers) {
         //insert a new post
        insertPost(newPost, images);
        // insert images of the post
        postImageService.createImage(images);
       // insert users tagged in post
        postTaggedUserService.createTaggedUsers(postTaggedUsers);
        return null;

    }

    @Override
    public Posts insertPost(Posts newPost, List<PostImages> images) {
        Posts post = Posts.builder()
                .id(newPost.getId())
                .content(newPost.getContent())
                .postOwner(newPost.getPostOwner())
                .privacyStatus(newPost.getPrivacyStatus())
                .postedAt(Timestamp.valueOf(LocalDateTime.now()))
                .isDeleted(false)
                .build();
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Posts updatePost(Posts updatePost, List<PostImages> images) {
        return null;
    }

    @Override
    public boolean deletePost(String postId) {
        return false;
    }
}
