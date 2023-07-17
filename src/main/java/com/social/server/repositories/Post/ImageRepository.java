package com.social.server.repositories.Post;


import com.social.server.dtos.ImageDTO;
import com.social.server.entities.Post.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images, String> {
    List<ImageDTO> findAllByPostId(String postId);
}
