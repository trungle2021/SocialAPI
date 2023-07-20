package com.social.server.repositories;

import com.social.server.dtos.CommentDTO;
import com.social.server.entities.Post.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comments,String> {
     String findAllParentCommentsByParentIdAndType = """
                     SELECT
                     co.id,
                     co.postId,
                     co.content,
                     co.imageUrl,
                     co.owner,
                     co.parentId,
                     co.likeCount,
                     co.isDeleted,
                     co.postedAt,
                     co.updatedAt,
                     (select count(*) from Comments c where c.parentId = co.id) as childCommentCount
                     FROM
                         Comments co
                     WHERE
                          co.parentId = :parentId
                       AND co.type = :type
                 
             """;
//    @Query(value = findAllParentCommentsByParentIdAndType)
    @Query("SELECT new com.social.server.dtos.CommentDTO(co.id,co.postId,co.content,co.imageUrl,co.owner, co.parentId,co.likeCount,co.isDeleted,co.postedAt, co.updatedAt, (select count(*) from Comments c where c.parentId = co.id) )" +
            "FROM Comments co" +
            " WHERE co.parentId = :parentId AND co.type = :type")
    List<CommentDTO> findAllParentCommentsByParentIdAndType(String parentId, String type, Pageable pageable);
}
