package com.social.server.repositories.Post;

import com.social.server.entities.Post.LikeCommentPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentPostRepository extends JpaRepository<LikeCommentPosts, String> {
}
