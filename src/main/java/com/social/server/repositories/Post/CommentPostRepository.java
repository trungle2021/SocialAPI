package com.social.server.repositories.Post;

import com.social.server.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostRepository extends JpaRepository<Posts, String> {
}
