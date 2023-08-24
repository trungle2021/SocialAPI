package com.social.server.services.Post.Like;

import com.social.server.entities.Post.Likes;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeService {
    int getLikeCountOfParentByParentId(String parentId);

    List<Likes> getLikeListOfParentByParentId(String parentId);
}
