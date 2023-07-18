package com.social.server.services;

import com.social.server.dtos.ImageDTO;
import com.social.server.entities.Post.Images;

import java.util.List;

public interface ImageService {
    List<ImageDTO> getImagesByPostId(String postId);
    ImageDTO getImage(String postId,String imageId);
    List<ImageDTO> createImage(List<ImageDTO> files, String postId,String privacyId);
    List<ImageDTO>  updateImage(List<ImageDTO> imagesToUpdate, String postId,String privacyId);
}
