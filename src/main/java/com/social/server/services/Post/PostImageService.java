package com.social.server.services.Post;

import com.social.server.entities.PostImages;

import java.util.List;

public interface PostImageService {
    PostImages getImagesByPostId(String postId);
    PostImages getImage(String postId,String imageId);
    List<PostImages>  createImage(List<PostImages> postImages);
    PostImages  updateImage(String imageId,String postId);
    boolean deleteImage(String imageId);
}
