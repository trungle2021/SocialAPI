package com.social.server.services.implement;

import com.social.server.dtos.*;
import com.social.server.entities.Post.Posts;
import com.social.server.entities.Privacies;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.JPA.Post.PostRepository;
import com.social.server.services.*;
import com.social.server.utils.EntityMapper;
import com.social.server.utils.FriendStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collections;
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
    private final FriendService friendService;
    private final PostTaggedUserService postTaggedUserService;

    private static List<CommentDTO> getCommentsByPostId(Page<CommentDTO> commentDTOS, PostDTO postDTO) {
        if (commentDTOS.isEmpty()) {
            return Collections.emptyList();
        }
        return commentDTOS.stream().filter(comment -> comment.getPostId().equals(postDTO.getId())).toList();
    }

    private static List<ImageDTO> getImagesByPostId(List<ImageDTO> imageDTOS, PostDTO postDTO) {
        if (imageDTOS.isEmpty()) {
            return Collections.emptyList();
        }
        return imageDTOS.stream().filter(image -> image.getParentId().equals(postDTO.getId())).toList();
    }



    @Override
    public Page<PostResponseDTO> getPostsByUserIdWithPagination(String userId, int offset, int limit, String sortBy) {
        Pageable pageable;
        if (StringUtils.hasText(sortBy)) {
            pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortBy));
        } else {
            pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "posted_at"));
        }

        //get all posts of user by userId
        List<PostDTO> postDTOS = postRepository.findAllByOwner(userId, pageable)
                .stream()
                .map(item -> EntityMapper.mapToDto(item, PostDTO.class))
                .toList();


        List<String> listPostId = getListPostId(postDTOS);
        if (listPostId.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }
        List<PostResponseDTO> postResponseDTOList = listPostId.parallelStream().map(item -> getPostByPostId(item, 0, 3)).toList();
        return new PageImpl<>(postResponseDTOList);
    }

    private static List<String> getListPostId(List<PostDTO> postDTOS) {
        List<String> listPostId = postDTOS.stream()
                .filter(item -> item.getType().equals("POST") && item.getParentId() == null)
                .map(PostDTO::getId)
                .toList();
        if (listPostId.isEmpty()) {
            return Collections.emptyList();
        } else {
            return listPostId;
        }
    }

    @Override
    public PostResponseDTO getPostByPostId(String postParentId, int offsetComment, int limitComment) {
        //get one posts by postParentId
        Posts posts = findPostById(postParentId);
        int likeCount = likeService.getLikeCountOfParentByParentId(postParentId);
        PostDTO postDTO = EntityMapper.mapToDto(posts, PostDTO.class);
        postDTO.setLikeCount(likeCount);
        postDTO.setPrivacyStatus(posts.getPrivacies().getPrivacyType());

        int shareCount = postRepository.getShareCountByPostId(postParentId);
        //get images of post
        List<ImageDTO> imageDTOS = imageService.getImagesByPostId(postParentId);
        //get comments
        Page<CommentDTO> commentDTOS = commentService.getChildCommentByParentId(postParentId, offsetComment, limitComment);
        //get tagged users
        List<PostTaggedUserDTO> taggedUsers = postTaggedUserService.getTaggedUsersByPostId(postParentId);

        return new PostResponseDTO(postDTO, getImagesByPostId(imageDTOS, postDTO), taggedUsers, getCommentsByPostId(commentDTOS, postDTO), shareCount);
    }


    public Posts findPostById(String postId) {
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
        Posts post = findPostById(updatedPostId);
        String content = updatePostDTO.getContent() == null ? post.getContent() : updatePostDTO.getContent();
        Privacies privacy = updatePostDTO.getPrivacyStatus() == null ? post.getPrivacies() : updatedPrivacy;
        Boolean isDeleted = updatePostDTO.getIsDeleted() == null ? post.getIsDeleted() : updatePostDTO.getIsDeleted();
        post.setContent(content);
        post.setPrivacies(privacy);
        post.setIsDeleted(isDeleted);
        return EntityMapper.mapToDto(postRepository.save(post), PostDTO.class);
    }

    @Override
    public Page<PostResponseDTO> getNewsFeed(String userId, int offset, int limit) {
        //get all friend list
        List<String> friendIdList = getFriendIdList(userId);
        //get post for each friend
        List<PostResponseDTO> postResponseDTOS = getPostForEachFriend(friendIdList);

        return new PageImpl<>(postResponseDTOS);
    }

    private List<String> getFriendIdList(String userId) {
        FriendListDTO friendList =  friendService.getFriendListByStatus(userId, FriendStatus.ACCEPTED.toString());
        return friendList.getFriendList()
                .stream()
                .map(FriendDTO::getId)
                .toList();
    }

    private List<PostResponseDTO> getPostForEachFriend(List<String> friendIdList) {
        return friendIdList.stream()
                .flatMap(item -> getPostsByUserIdWithPagination(item,0,1,"postedAt").stream())
                .toList();
    }



}

