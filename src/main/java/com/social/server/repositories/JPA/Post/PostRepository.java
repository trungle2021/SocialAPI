package com.social.server.repositories.JPA.Post;

import com.social.server.entities.Post.Posts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, String> {
//    @Query("Select p from Posts p where p.owner = :userId")
    List<Posts> findAllByOwner(String userId, Pageable pageable);
    List<Posts> findAllByOwner(String userId, Sort pageable);
    Optional<Posts> getPostById(String postId);

    @Query("select count(*) from Posts p where p.parentId = :postId")
    int getShareCountByPostId(String postId);
}
