package com.social.server.repositories;

import com.social.server.entities.Post.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes,String> {
    @Query("select count(*) from Likes l where l.parentId = :parentId")
    int getLikeCountOfParentByParentId(String parentId);

    @Query("select l from Likes l join Users u  on u.id = l.userId where l.parentId=:parentId")
    List<Likes> getLikeListOfParentByParentId(String parentId);
}
