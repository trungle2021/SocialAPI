package com.social.server.services;

import com.social.server.dtos.PostTaggedUserDTO;
import com.social.server.entities.User.Users;

import java.util.List;

public interface PostTaggedUserService {
    List<Users> getTaggedUsers(String postId);
    List<PostTaggedUserDTO> createTaggedUsers(List<PostTaggedUserDTO> taggedUsersList,String postId);
    List<PostTaggedUserDTO> updateTaggedUsers(List<PostTaggedUserDTO> taggedUsersList);
    boolean deleteTaggedUsers(List<PostTaggedUserDTO> taggedUser);
}
