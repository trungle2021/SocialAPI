package com.social.server.services.implement;

import com.social.server.dtos.*;
import com.social.server.entities.Post.Posts;
import com.social.server.entities.Privacies;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.*;
import com.social.server.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PrivacyService privacyService;
    private final PostBaseService postBaseService;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final ImageService imageService;
    private final LikeService likeService;
    private final PostTaggedUserService postTaggedUserService;

    private static String getPostParentId(List<PostDTO> postDTOS) {
        String postId;
        Optional<PostDTO> firstPost = postDTOS.stream()
                .filter(item -> item.getType().equals("POST") && item.getParentId() == null)
                .findFirst();

        if (firstPost.isPresent()) {
            postId = firstPost.get().getId();
        }else{
            return "";
        }
        return postId;
    }

    private static List<CommentDTO> getCommentsByPostId(Page<CommentDTO> commentDTOS, PostDTO postDTO) {
        if (commentDTOS.isEmpty()) {
            return new ArrayList<>();
        }
        return commentDTOS.stream().filter(comment -> comment.getPostId().equals(postDTO.getId())).toList();
    }

    private static List<ImageDTO> getImagesByPostId(List<ImageDTO> imageDTOS, PostDTO postDTO) {
        if (imageDTOS.isEmpty()) {
            return new ArrayList<>();
        }
        return imageDTOS.stream().filter(image -> image.getParentId().equals(postDTO.getId())).toList();
    }

    @Override
    public Page<PostResponseDTO> getPostsByUserIdWithPagination(String userId, int offset, int limit, String field) {
        Pageable pageable;
        if (StringUtils.hasText(field)) {
            pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, field));
        } else {
            pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "posted_at"));
        }

        //get all posts of user by userId
        List<PostDTO> postDTOS = postRepository.findAllByOwner(userId, pageable)
                .stream()
                .map(item -> EntityMapper.mapToDto(item, PostDTO.class))
                .toList();

        int shareCount = (int) postDTOS.stream().filter(item -> item.getParentId() != null).count();

//        Map<String, Object> result = postRepository.findAllPostsByUserId(userId, pageable)
//                .stream()
//                .collect(Collectors.collectingAndThen(
//                        Collectors.partitioningBy(
//                                item -> item.getParentId() == null,
//                                Collectors.mapping(item -> EntityMapper.mapToDto(item, PostDTO.class), Collectors.toList())
//                        ),
//                        map -> {
//                            Map<String, Object> resultMap = new HashMap<>();
//                            resultMap.put("shareCount", map.get(true).size());
//                            resultMap.put("postDTOList", map.get(false));
//                            return resultMap;
//                        }
//                ));

        String postId;

        postId = getPostParentId(postDTOS);
        //get images of post
        List<ImageDTO> imageDTOS = imageService.getImagesByPostId(postId);
        //get comments
        Page<CommentDTO> commentDTOS = commentService.getCommentByParentId(postId, 0, 5);
        //get tagged users
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.getTaggedUsersByPostId(postId);


        List<PostResponseDTO> postResponseDTO = postDTOS.parallelStream().map(
                postDTO -> new PostResponseDTO(postDTO, getImagesByPostId(imageDTOS, postDTO), taggedUsers, getCommentsByPostId(commentDTOS, postDTO), shareCount)
        ).toList();
        return new PageImpl<>(postResponseDTO);
    }

    @Override
    public PostResponseDTO getOnePostParentByPostId(String postParentId) {
        //get one posts by postParentId
        Posts posts = getPostById(postParentId);
        int likeCount = likeService.getLikeCountOfParentByParentId(postParentId);
        PostDTO postDTO = EntityMapper.mapToDto(posts, PostDTO.class);
        postDTO.setLikeCount(likeCount);
        postDTO.setPrivacyStatus(posts.getPrivacies().getPrivacyType());

        int shareCount = postRepository.getShareCountByPostId(postParentId);
        //get images of post
        List<ImageDTO> imageDTOS = imageService.getImagesByPostId(postParentId);
        //get comments
        Page<CommentDTO> commentDTOS = commentService.getCommentByParentId(postParentId, 0, 5);
        //get tagged users
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.getTaggedUsersByPostId(postParentId);

         return new PostResponseDTO(postDTO, getImagesByPostId(imageDTOS, postDTO), taggedUsers, getCommentsByPostId(commentDTOS, postDTO), shareCount);
    }

    @Override
    public Page<PostDTO> getPostsByUserIdWithSorting(String userId, String field) {
        List<PostDTO> postDTOS = postRepository.findAllByOwner(userId, Sort.by(Sort.Direction.ASC, field))
                .stream()
                .map(item -> EntityMapper.mapToDto(item, PostDTO.class))
                .toList();
        return new PageImpl<>(postDTOS);
    }

    @Override
    public Posts getPostById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found", "id", postId));
    }

    @Override
    @Transactional
    public PostResponseDTO createPost(PostRequestCreateDTO postRequestCreateDTO) {
        PostDTO newPost = postRequestCreateDTO.getNewPost();
        List<ImageDTO> postImages = postRequestCreateDTO.getPostImages();
        List<PostTaggedUserDTO> postTaggedUsers = postRequestCreateDTO.getPostTaggedUsers();

        //insert a new post
        PostDTO postDTO = insertPost(newPost);
        String postDTOId = postDTO.getId();

        // insert images of the post
        List<ImageDTO> imageURLs = null;
        if (postImages != null) {
            imageURLs = imageService.createImage(postImages, postDTOId);
        }
        // insert users tagged in post
        List<PostTaggedUserDTO> taggedUsers = null;
        if (postTaggedUsers != null) {
            taggedUsers = postTaggedUserService.createTaggedUsers(postTaggedUsers, postDTOId);
        }

        return PostResponseDTO.builder()
                .post(postDTO)
                .postImages(imageURLs)
                .postTaggedUsers(taggedUsers)
                .build();

    }

    @Override
    public PostDTO insertPost(PostDTO newPost) {
        String privacyStatus = newPost.getPrivacyStatus();
        Privacies privacies = EntityMapper.mapToEntity(privacyService.findByType(privacyStatus), Privacies.class);
        Posts post = Posts.builder()
                .content(newPost.getContent())
                .owner(newPost.getOwner())
                .parentId(Optional.ofNullable(newPost.getParentId()).orElse(null))
                .parentId(newPost.getParentId())
                .privacies(privacies)
                .postedAt(Instant.now())
                .isDeleted(false)
                .likeCount(0)
                .build();
        PostDTO postDTO = EntityMapper.mapToDto(postRepository.save(post), PostDTO.class);
        postDTO.setPrivacyStatus(privacies.getPrivacyType());
        return postDTO;
    }

    @Override
    public PostDTO sharePost(PostDTO sharePost) {
        return insertPost(sharePost);
    }

    @Override
    public PostResponseDTO updatePost(PostRequestUpdateDTO updateDTO) {
        String postId = updateDTO.getUpdatePost().getId();
        PostDTO postToUpdate = updateDTO.getUpdatePost();
        List<PostTaggedUserDTO> taggedUsersToUpdate = updateDTO.getPostTaggedUsers();
        List<ImageDTO> imagesToDelete = updateDTO.getImagesToDelete();
        List<ImageDTO> imagesToUpdate = updateDTO.getImagesToUpdate();

        // Check if postId is present
        PostDTO updatedPostDTO = editPost(postToUpdate);

        //handle images updates
        if (!imagesToDelete.isEmpty() && !imagesToUpdate.isEmpty()) {
            // Delete images first, then update (by creating new images)
            postBaseService.deleteAll(imagesToDelete);
            List<ImageDTO> updatedImages = imageService.updateImage(imagesToUpdate, postId);
            updateDTO.setImagesToUpdate(updatedImages);
        } else if (!imagesToDelete.isEmpty()) {
            // Only delete images
            postBaseService.deleteAll(imagesToDelete);
        } else {
            // Only update images
            List<ImageDTO> updatedImages = imageService.updateImage(imagesToUpdate, postId);
            updateDTO.setImagesToUpdate(updatedImages);
        }

        //update user tagged
        List<PostTaggedUserDTO> updatedTaggedUsers = null;
        if (!taggedUsersToUpdate.isEmpty()) {
            updatedTaggedUsers = postTaggedUserService.updateTaggedUsers(updateDTO.getPostTaggedUsers());
        }
        return PostResponseDTO.builder()
                .post(updatedPostDTO)
                .postImages(updateDTO.getImagesToUpdate())
                .postTaggedUsers(updatedTaggedUsers)
                .build();
    }


    @Override
    public PostDTO editPost(PostDTO updatePostDTO) {
        String updatedPostId = updatePostDTO.getId();
        Privacies updatedPrivacy = EntityMapper.mapToEntity(privacyService.findByType(updatePostDTO.getPrivacyStatus()), Privacies.class);
        Posts post = getPostById(updatedPostId);
        String content = updatePostDTO.getContent() == null ? post.getContent() : updatePostDTO.getContent();
        Privacies privacy = updatePostDTO.getPrivacyStatus() == null ? post.getPrivacies() : updatedPrivacy;
        Boolean isDeleted = updatePostDTO.getIsDeleted() == null ? post.getIsDeleted() : updatePostDTO.getIsDeleted();
        post.setContent(content);
        post.setPrivacies(privacy);
        post.setIsDeleted(isDeleted);
        return EntityMapper.mapToDto(postRepository.save(post), PostDTO.class);
    }

}

