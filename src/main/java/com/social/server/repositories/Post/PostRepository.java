package com.social.server.repositories.Post;

import com.social.server.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, String> {
    @Query("Select p from Posts p where p.postOwner = :userId")
    List<Posts> findAllPostsByUserId(String userId);
}
