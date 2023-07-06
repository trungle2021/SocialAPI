package com.social.server.services.implement;

import com.social.server.dtos.PostDTO;
import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.PostImageService;
import com.social.server.services.PostService;
import com.social.server.services.PostTaggedUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Posts getPostById(String userId, String postId) {
        Posts posts = postRepository.getPostById(userId,postId);
        if (posts == null) {
            throw new ResourceNotFoundException("No post with userId: " + userId + "and postId: " + postId);
        }
        return posts;
    }

    @Override
    @Transactional
    public PostDTO createPost(Posts newPost, List<MultipartFile> postImages, List<PostTaggedUsers> postTaggedUsers) {
         //insert a new post
       Posts post = insertPost(newPost);
        // insert images of the post
        List<String> imageURLs = postImageService.createImage(postImages, newPost.getId());
       // insert users tagged in post
        List<PostTaggedUsers> taggedUsers = postTaggedUserService.createTaggedUsers(postTaggedUsers);

        return PostDTO.builder()
                .newPost(post)
                .imageOfPosts(imageURLs)
                .usersTaggedOfPosts(taggedUsers)
                .build();

    }

    @Override
    public Posts insertPost(Posts newPost) {
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
    public PostDTO updatePost(Posts updatePost, List<MultipartFile> images, List<PostTaggedUsers> postTaggedUsers) {
        Posts post;
        List<String> imageURLs = null;
        List<PostTaggedUsers> taggedUsers = null;
        //select post by userid and postid
        //check if postId being attached to post object
        if (updatePost.getId() == null) {
            throw new SocialAppException(HttpStatus.BAD_REQUEST,"Missing PostId to update");
        }else{
          post = editPost(updatePost);
        }

        if(images.isEmpty()){
            imageURLs = postImageService.updateImage(images, updatePost.getId());
        }
        //update user tagged
        if(postTaggedUsers.isEmpty()){
            //do something
            taggedUsers =  postTaggedUserService.updateTaggedUsers(postTaggedUsers);
        }
        return PostDTO.builder()
                .newPost(post)
                .imageOfPosts(imageURLs)
                .usersTaggedOfPosts(taggedUsers)
                .build();

    }

    @Override
    public Posts editPost(Posts updatePost) {
        Posts post = getPostById(updatePost.getPostOwner(), updatePost.getId());

        String content = updatePost.getContent() == null ? post.getContent() : updatePost.getContent();
        String privacyStatus =  updatePost.getPrivacyStatus() == null ? post.getPrivacyStatus() : updatePost.getPrivacyStatus();
        Boolean isDeleted =  updatePost.getIsDeleted() == null ? post.getIsDeleted() : updatePost.getIsDeleted();

        post.setContent(content);
        post.setPrivacyStatus(privacyStatus);
        post.setIsDeleted(isDeleted);
       return postRepository.save(post);
    }


    @Override
    public boolean deletePost(String postId) {
        return false;
    }
}
