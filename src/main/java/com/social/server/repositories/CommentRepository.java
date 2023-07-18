package com.social.server.repositories;

import com.social.server.entities.Post.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments,String> {
    List<Comments> findAllByParentId(String parentId, Pageable pageable);
}
