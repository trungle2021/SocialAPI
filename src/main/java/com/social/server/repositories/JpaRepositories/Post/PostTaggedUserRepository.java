package com.social.server.repositories.JpaRepositories.Post;

import com.social.server.entities.Post.PostTaggedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTaggedUserRepository extends JpaRepository<PostTaggedUsers, String> {
        List<PostTaggedUsers>  findAllByPostId(String postId);
}
