package com.social.server.repositories.JPA.Post;

import com.social.server.entities.Post.PostBase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostBaseRepository extends JpaRepository<PostBase,String> {
}
