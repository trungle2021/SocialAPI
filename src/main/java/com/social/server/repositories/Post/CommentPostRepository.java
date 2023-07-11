package com.social.server.repositories.Post;

import com.social.server.entities.Post.CommentPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostRepository extends JpaRepository<CommentPosts, String> {
}
