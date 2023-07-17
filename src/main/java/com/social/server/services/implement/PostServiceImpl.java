package com.social.server.services.implement;

import com.social.server.dtos.*;
import com.social.server.entities.Post.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.CommentService;
import com.social.server.services.ImageService;
import com.social.server.services.PostService;
import com.social.server.services.PostTaggedUserService;
import com.social.server.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final ImageService imageService;
    private final PostTaggedUserService postTaggedUserService;




    @Override
    public Page<PostResponseDTO> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field) {
        Pageable pageable;
        if(StringUtils.hasText(field)){
            pageable = PageRequest.of(offset,limit,Sort.by(Sort.Direction.ASC, field));
        }else{
             pageable = PageRequest.of(offset,limit,Sort.by(Sort.Direction.ASC, "posted_at"));
        }

        //get new Post
        List<PostDTO> postDTOS = postRepository.findAllPostsByUserId(userId,pageable)
                .stream()
                .map(item -> EntityMapper.mapToDto(item,PostDTO.class))
                .toList();
        String postId = postDTOS.stream().findFirst().get().getId();
        //get images of post
        List<ImageDTO> imageDTOS = imageService.getImagesByPostId(postId);
        //get comments
        Page<CommentDTO> commentDTOS = commentService.getCommentByParentId(postId,1,2);
        //get tagged users
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.getTaggedUsers(postId);


        List<PostResponseDTO> postResponseDTO = postDTOS.parallelStream().map(
                postDTO -> new PostResponseDTO(postDTO, getImageDTOS(imageDTOS, postDTO), taggedUsers,getComments(commentDTOS.stream().toList(), postDTO))
        ).toList();
        return new PageImpl<>(postResponseDTO);
    }
    private static List<CommentDTO> getComments(List<CommentDTO> commentDTOS, PostDTO postDTO) {
        return  commentDTOS.stream().filter(comment -> comment.getPostId().equals(postDTO.getId())).toList();
    }

    private static List<ImageDTO> getImageDTOS(List<ImageDTO> imageDTOS, PostDTO postDTO) {
        return imageDTOS.stream().filter(image -> image.getPostId().equals(postDTO.getId())).toList();
    }

    @Override
    public Page<PostDTO> getPostsByUserIdWithSorting(String userId, String field) {
       List<PostDTO> postDTOS =  postRepository.findAllPostsByOwner(userId,Sort.by(Sort.Direction.ASC,field))
               .stream()
               .map(item -> EntityMapper.mapToDto(item,PostDTO.class))
               .toList();
        return new PageImpl<>(postDTOS);
    }

    @Override
    public Posts getPostById(String postId) {
        Optional.ofNullable(postId).orElseThrow(() -> new SocialAppException(HttpStatus.BAD_REQUEST, "PostId cannot be null"));
        return postRepository.getPostById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found","id",postId));
    }

    @Override
    @Transactional
    public PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO) {
         //insert a new post
       PostDTO postDTO = insertPost(postRequestCreateDTO.getNewPost());
        // insert images of the post
        List<ImageDTO> imageURLs = imageService.createImage(postRequestCreateDTO.getPostImages(), postDTO.getId(),postDTO.getPrivacyId());
       // insert users tagged in post
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.createTaggedUsers(postRequestCreateDTO.getPostTaggedUsers(),postDTO.getId());

        return PostResponseDTO.builder()
                .post(postDTO)
                .postImages(imageURLs)
                .postTaggedUsers(taggedUsers)
                .build();

    }

    @Override
    public PostDTO insertPost(PostDTO newPost) {
        Posts post = Posts.builder()
                .content(newPost.getContent())
                .owner(newPost.getOwner())
                .privacyId(newPost.getPrivacyId())
                .postedAt(Instant.now())
                .isDeleted(false)
                .likeCount(0)
                .build();
        return EntityMapper.mapToDto(postRepository.save(post),PostDTO.class);
    }

    @Override
    public PostResponseDTO updatePost(PostRequestUpdateDTO updateDTO) {
        String postId = updateDTO.getUpdatePost().getId();
        PostDTO postToUpdate = updateDTO.getUpdatePost();
        List<PostTaggedUserDTO> taggedUsersToUpdate = updateDTO.getPostTaggedUsers();
        List<ImageDTO> imagesToDelete = updateDTO.getImagesToDelete();
        List<ImageDTO> imagesToUpdate = updateDTO.getImagesToUpdate();

        // Check if postId is present
        Optional.ofNullable(postId).orElseThrow(() -> new SocialAppException(HttpStatus.BAD_REQUEST,"Missing PostId to update"));
        PostDTO updatedPostDTO = editPost(postToUpdate);

        //handle images updates
        if(!imagesToDelete.isEmpty() && !imagesToUpdate.isEmpty()){
            // Delete images first, then update (by creating new images)
            imageService.deleteImage(imagesToDelete,postId);
            List<ImageDTO> updatedImages =  imageService.updateImage(imagesToUpdate,postId,updatedPostDTO.getPrivacyId());
            updateDTO.setImagesToUpdate(updatedImages);
        }else if(!imagesToDelete.isEmpty()){
            // Only delete images
            imageService.deleteImage(imagesToDelete,postId);
        }else{
            // Only update images
            List<ImageDTO> updatedImages =  imageService.updateImage(imagesToUpdate,postId,updatedPostDTO.getPrivacyId());
            updateDTO.setImagesToUpdate(updatedImages);
        }

        //update user tagged
        List<PostTaggedUserDTO> updatedTaggedUsers = null;
        if(!taggedUsersToUpdate.isEmpty()){
            updatedTaggedUsers =  postTaggedUserService.updateTaggedUsers(updateDTO.getPostTaggedUsers());
        }
        return PostResponseDTO.builder()
                .post(updatedPostDTO)
                .postImages(updateDTO.getImagesToUpdate())
                .postTaggedUsers(updatedTaggedUsers)
                .build();
    }


    @Override
    public PostDTO editPost(PostDTO updatePost) {
        String postId = updatePost.getId();

        Posts post = getPostById(postId);
        String content = updatePost.getContent() == null ? post.getContent() : updatePost.getContent();
        String privacyStatus =  updatePost.getPrivacyId() == null ? post.getPrivacyId() : updatePost.getPrivacyId();
        Boolean isDeleted =  updatePost.getIsDeleted() == null ? post.getIsDeleted() : updatePost.getIsDeleted();
        post.setContent(content);
        post.setPrivacyId(privacyStatus);
        post.setIsDeleted(isDeleted);
       return EntityMapper.mapToDto(postRepository.save(post),PostDTO.class);
    }


    @Override
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }
}
