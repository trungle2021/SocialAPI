package com.social.server.services.implement;

import com.social.server.entities.PostImages;
import com.social.server.repositories.Post.PostImageRepository;
import com.social.server.services.PostImageService;
import com.social.server.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;
    private final StorageService storageService;
    @Override
    public PostImages getImagesByPostId(String postId) {
        return null;
    }

    @Override
    public PostImages getImage(String postId, String imageId) {
        return null;
    }

    @Override
    public List<String> createImage(List<MultipartFile> files, String postId) {
        List<String> urlLinks = new ArrayList<>();
        for (var file : files) {
           String url = storageService.uploadFile(file);
            PostImages postImages = PostImages.builder()
                    .imageUrl(url)
                    .postId(postId)
                    .isDeleted(false)
                    .build();
            postImageRepository.save(postImages);
            urlLinks.add(url);
        }
        return urlLinks;
    }

    @Override
    public List<String> updateImage(List<MultipartFile> files, List<String> imageToDelete, String postId) {
        // get ID list
        //delete image


        //get image from db
        List<String> imageFileNames  = new ArrayList<>();
        for (var file : files) {
            imageFileNames.add(file.getOriginalFilename());
        }

        return null;
    }

    @Override
    public boolean deleteImage(String imageId) {
        return false;
    }
}
