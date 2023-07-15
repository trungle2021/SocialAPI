package com.social.server.services;

import com.social.server.dtos.ImageDTO;
import com.social.server.entities.Post.Images;

import java.util.List;

public interface ImageService {
    Images getImagesByPostId(String postId);
    Images getImage(String postId,String imageId);
    List<ImageDTO> createImage(List<ImageDTO> files, String postId);
    List<ImageDTO>  updateImage(List<ImageDTO> imagesToUpdate, String postId);
    void deleteImage(List<ImageDTO> imagesToDelete, String id);
}
