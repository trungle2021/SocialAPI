package com.social.server.services;

import com.social.server.dtos.PostImageDTO;
import com.social.server.dtos.PostRequestUpdateDTO;
import com.social.server.entities.PostImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostImageService {
    PostImages getImagesByPostId(String postId);
    PostImages getImage(String postId,String imageId);
    List<PostImageDTO> createImage(List<PostImageDTO> files, String postId);
    List<PostImageDTO>  updateImage(List<PostImageDTO> imagesToUpdate, List<PostImageDTO> imagesToDelete);
    boolean deleteImage(String imageId);
}
