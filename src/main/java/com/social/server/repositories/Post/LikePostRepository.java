package com.social.server.repositories.Post;

import com.social.server.entities.Post.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, String> {
}
