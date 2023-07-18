package com.social.server.services;


import com.social.server.dtos.SharedPostDTO;
import com.social.server.entities.Post.SharedPosts;

import java.util.List;

public interface SharePostService{
    List<SharedPostDTO> getSharePost();
    Integer shareCount(String postId);

}
