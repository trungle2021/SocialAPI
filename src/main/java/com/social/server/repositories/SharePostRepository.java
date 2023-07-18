package com.social.server.repositories;

import com.social.server.entities.Post.SharedPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharePostRepository extends JpaRepository<SharedPosts,String> {

}
