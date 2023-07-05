package com.social.server.repositories.Post;

import com.social.server.entities.PostImages;
import com.social.server.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImages, String> {
}
