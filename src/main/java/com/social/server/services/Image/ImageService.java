package com.social.server.services.Image;

import com.social.server.dtos.ImageDTO;

import java.util.List;

public interface ImageService {
    List<ImageDTO> getImagesByPostId(String postId);
    ImageDTO getImage(String postId,String imageId);
    List<ImageDTO> createImage(List<ImageDTO> files, String postId);
    List<ImageDTO>  updateImage(List<ImageDTO> imagesToUpdate, String postId);
}
