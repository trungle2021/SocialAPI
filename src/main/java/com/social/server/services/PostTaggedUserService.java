package com.social.server.services;

import com.social.server.entities.PostTaggedUsers;
import com.social.server.entities.Users;

import java.util.List;

public interface PostTaggedUserService {
    List<Users> getTaggedUsers(String postId);
    List<PostTaggedUsers> createTaggedUsers(List<PostTaggedUsers> taggedUsersList);
    List<PostTaggedUsers> updateTaggedUsers(List<PostTaggedUsers> taggedUsersList);
    boolean deleteTaggedUsers(PostTaggedUsers taggedUser);
}
