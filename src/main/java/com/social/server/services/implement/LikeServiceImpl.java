package com.social.server.services.implement;

import com.social.server.entities.Post.Likes;
import com.social.server.repositories.JpaRepositories.LikeRepository;
import com.social.server.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    @Override
    public int getLikeCountOfParentByParentId(String parentId) {
        return likeRepository.getLikeCountOfParentByParentId(parentId);
    }

    @Override
    public List<Likes> getLikeListOfParentByParentId(String parentId) {
        return likeRepository.getLikeListOfParentByParentId(parentId);
    }
}
