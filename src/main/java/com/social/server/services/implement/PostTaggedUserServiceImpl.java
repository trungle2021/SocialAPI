package com.social.server.services.implement;

import com.social.server.dtos.PostTaggedUserDTO;
import com.social.server.entities.Post.PostTaggedUsers;
import com.social.server.entities.Post.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.repositories.Post.PostTaggedUserRepository;
import com.social.server.services.PostTaggedUserService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTaggedUserServiceImpl implements PostTaggedUserService {
    private final PostTaggedUserRepository postTaggedRepository;
    private final PostRepository postRepository;
    @Override
    public List<PostTaggedUserDTO> getTaggedUsersByPostId(String postId) {
        Posts post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found","id",postId));
        return postTaggedRepository.getTaggedUsersByPostId(post.getId()).stream().map(item->EntityMapper.mapToDto(item,PostTaggedUserDTO.class)).toList();
    }

    @Override
    public List<PostTaggedUserDTO> createTaggedUsers(List<PostTaggedUserDTO> taggedUsersList,String postId) {

        List<PostTaggedUsers> postUsers = taggedUsersList.stream()
                .map(item -> EntityMapper.mapToEntity(item,PostTaggedUsers.class)
                )
                .peek(item->item.setPostId(postId)).toList();
        return postTaggedRepository.saveAll(postUsers).stream().map(item -> EntityMapper.mapToDto(item, PostTaggedUserDTO.class)).toList();
    }

    @Override
    public List<PostTaggedUserDTO> updateTaggedUsers(List<PostTaggedUserDTO> taggedUsersList) {
        return null;
    }

    @Override
    public boolean deleteTaggedUsers(List<PostTaggedUserDTO> taggedUsers) {
        return false;
    }
}
