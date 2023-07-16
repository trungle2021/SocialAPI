package com.social.server.repositories.Post;

import com.social.server.entities.Post.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, String> {
    @Query("Select p from Posts p where p.owner = :userId")
    List<Posts> findAllPostsByUserId(String userId);

    @Query("Select p from Posts p where p.owner = :userId and p.id = :postId")
    Optional<Posts> getPostById(String userId, String postId);
}
