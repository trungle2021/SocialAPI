package com.social.server.repositories.Post;

import com.social.server.dtos.PostTaggedUserDTO;
import com.social.server.entities.Post.PostTaggedUsers;
import com.social.server.entities.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostTaggedUserRepository extends JpaRepository<PostTaggedUsers, String> {
        List<PostTaggedUsers>  findAllByPostId(String postId);
}
