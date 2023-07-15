package com.social.server.repositories.Post;


import com.social.server.entities.Post.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, String> {
}
