package com.social.server.services.implement.Post;

import com.social.server.entities.PostImages;
import com.social.server.repositories.Post.PostImageRepository;
import com.social.server.services.Post.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;
    @Override
    public PostImages getImagesByPostId(String postId) {
        return null;
    }

    @Override
    public PostImages getImage(String postId, String imageId) {
        return null;
    }

    @Override
    public List<PostImages> createImage(List<PostImages> postImages) {
        return postImageRepository.saveAll(postImages);
    }

    @Override
    public PostImages updateImage(String imageId, String postId) {
        return null;
    }

    @Override
    public boolean deleteImage(String imageId) {
        return false;
    }
}
