package com.social.server.services;

import com.social.server.dtos.PostTaggedUserDTO;

import java.util.List;

public interface PostTaggedUserService {
    List<PostTaggedUserDTO> getTaggedUsers(String postId);
    List<PostTaggedUserDTO> createTaggedUsers(List<PostTaggedUserDTO> taggedUsersList,String postId);
    List<PostTaggedUserDTO> updateTaggedUsers(List<PostTaggedUserDTO> taggedUsersList);
    boolean deleteTaggedUsers(List<PostTaggedUserDTO> taggedUser);
}
