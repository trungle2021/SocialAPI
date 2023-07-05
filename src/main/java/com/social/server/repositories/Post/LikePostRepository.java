package com.social.server.repositories.Post;

import com.social.server.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<Posts, String> {
}
