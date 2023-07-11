package com.social.server.services;

import com.social.server.dtos.PostImageDTO;
import com.social.server.entities.Post.PostImages;

import java.util.List;

public interface PostImageService {
    PostImages getImagesByPostId(String postId);
    PostImages getImage(String postId,String imageId);
    List<PostImageDTO> createImage(List<PostImageDTO> files, String postId);
    List<PostImageDTO>  updateImage(List<PostImageDTO> imagesToUpdate, String postId);
    boolean deleteImage(List<PostImageDTO> imagesToDelete, String id);
}
