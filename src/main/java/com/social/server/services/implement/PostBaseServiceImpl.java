package com.social.server.services.implement;

import com.social.server.entities.Post.PostBase;
import com.social.server.repositories.Post.PostBaseRepository;
import com.social.server.services.PostBaseService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostBaseServiceImpl implements PostBaseService {
    private final PostBaseRepository postBaseRepository;
    @Override
    public void deleteChild(String childId) {
        postBaseRepository.deleteById(childId);
    }

    @Override
    public <T> void deleteAll(List<T>  children) {
        List<PostBase> postBase = children.stream().map(item -> EntityMapper.mapToEntity(item,PostBase.class)).toList();
        postBaseRepository.deleteAll(postBase);
    }
}
