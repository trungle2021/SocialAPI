package com.social.server.services;

import com.social.server.entities.PostImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostImageService {
    PostImages getImagesByPostId(String postId);
    PostImages getImage(String postId,String imageId);
    List<String>  createImage(List<MultipartFile> files,String postId);
    List<String>  updateImage(List<MultipartFile> files,String postId);
    boolean deleteImage(String imageId);
}
