package com.social.server.repositories.Post;

import com.social.server.entities.Post.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImages, String> {
}
