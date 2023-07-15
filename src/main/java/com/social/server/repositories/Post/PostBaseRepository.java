package com.social.server.repositories.Post;

import com.social.server.entities.Post.PostBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PostBaseRepository extends JpaRepository<PostBase,String> {
}
