package com.social.server.services.implement;

import com.social.server.dtos.*;
import com.social.server.entities.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.PostImageService;
import com.social.server.services.PostService;
import com.social.server.services.PostTaggedUserService;
import com.social.server.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public Posts getPostById(String userId, String postId) {
        Posts posts = postRepository.getPostById(userId,postId);
        if (posts == null) {
            throw new ResourceNotFoundException("No post with userId: " + userId + "and postId: " + postId);
        }
        return posts;
    }

    @Override
    @Transactional
    public PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO) {
         //insert a new post
       PostDTO postDTO = insertPost(postRequestCreateDTO.getNewPost());
        // insert images of the post
        List<PostImageDTO> imageURLs = postImageService.createImage(postRequestCreateDTO.getPostImages(), postRequestCreateDTO.getNewPost().getId());
       // insert users tagged in post
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.createTaggedUsers(postRequestCreateDTO.getPostTaggedUsers());

        return PostResponseDTO.builder()
                .newPost(postDTO)
                .postImages(imageURLs)
                .postTaggedUsers(taggedUsers)
                .build();

    }

    @Override
    public PostDTO insertPost(PostDTO newPost) {
        Posts post = Posts.builder()
                .id(newPost.getId())
                .content(newPost.getContent())
                .postOwner(newPost.getPostOwner())
                .privacyStatus(newPost.getPrivacyStatus())
                .postedAt(Timestamp.valueOf(LocalDateTime.now()))
                .isDeleted(false)
                .build();
        return EntityMapper.mapToDto(postRepository.save(post),PostDTO.class);
    }

    @Override
    public PostResponseDTO updatePost(PostRequestUpdateDTO updateDTO) {
        String postId = updateDTO.getUpdatePost().getId();
        PostDTO postToUpdate = updateDTO.getUpdatePost();
        List<PostTaggedUserDTO> taggedUsersToUpdate = updateDTO.getPostTaggedUsers();
        List<PostImageDTO> imagesToDelete = updateDTO.getPostImagesToDelete();
        List<PostImageDTO> imagesToUpdate = updateDTO.getPostImagesToUpdate();

        // Check if postId is present
        if (postId == null) {
            throw new SocialAppException(HttpStatus.BAD_REQUEST,"Missing PostId to update");
        }
        PostDTO updatedPostDTO = editPost(postToUpdate);

        //handle images updates
        if(!imagesToDelete.isEmpty() && !imagesToUpdate.isEmpty()){
            // Delete images first, then update (by creating new images)
            postImageService.deleteImage(imagesToDelete,postId);
            List<PostImageDTO> updatedImages =  postImageService.updateImage(imagesToUpdate,postId);
            updateDTO.setPostImagesToUpdate(updatedImages);
        }else if(!imagesToDelete.isEmpty()){
            // Only delete images
            postImageService.deleteImage(imagesToDelete,postId);
        }else{
            // Only update images
            List<PostImageDTO> updatedImages =  postImageService.updateImage(imagesToUpdate,postId);
            updateDTO.setPostImagesToUpdate(updatedImages);
        }

        //update user tagged
        List<PostTaggedUserDTO> updatedTaggedUsers = null;
        if(!taggedUsersToUpdate.isEmpty()){
            updatedTaggedUsers =  postTaggedUserService.updateTaggedUsers(updateDTO.getPostTaggedUsers());
        }
        return PostResponseDTO.builder()
                .newPost(updatedPostDTO)
                .postImages(updateDTO.getPostImagesToUpdate())
                .postTaggedUsers(updatedTaggedUsers)
                .build();
    }

    @Override
    public PostDTO editPost(PostDTO updatePost) {
        Posts post = getPostById(updatePost.getPostOwner(), updatePost.getId());

        String content = updatePost.getContent() == null ? post.getContent() : updatePost.getContent();
        String privacyStatus =  updatePost.getPrivacyStatus() == null ? post.getPrivacyStatus() : updatePost.getPrivacyStatus();
        Boolean isDeleted =  updatePost.getIsDeleted() == null ? post.getIsDeleted() : updatePost.getIsDeleted();

        post.setContent(content);
        post.setPrivacyStatus(privacyStatus);
        post.setIsDeleted(isDeleted);
       return EntityMapper.mapToDto(postRepository.save(post),PostDTO.class);
    }


    @Override
    public boolean deletePost(String postId) {
        postRepository.deleteById(postId);
        return true;
    }
}
