package com.social.server.services.implement;

import com.social.server.dtos.ImageDTO;
import com.social.server.entities.Post.Images;
import com.social.server.repositories.Post.ImageRepository;
import com.social.server.services.ImageService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public Images getImagesByPostId(String postId) {
        return null;
    }

    @Override
    public Images getImage(String postId, String imageId) {
        return null;
    }

    @Override
    public List<ImageDTO> createImage(List<ImageDTO> data, String postId) {

        List<Images> images = data.stream().map(item -> EntityMapper.mapToEntity(item,Images.class)).toList();

        List<Images> listImagesAfterCreate = imageRepository.saveAll(images);

        return listImagesAfterCreate.stream().map(item -> EntityMapper.mapToDto(item,ImageDTO.class)).toList();
    }

    @Override
    public List<ImageDTO> updateImage(List<ImageDTO> imagesToUpdate, String postId) {
        List<Images> postsList = imagesToUpdate.stream().map(item -> EntityMapper.mapToEntity(item,Images.class)).toList();
        return imageRepository.saveAll(postsList).stream().map(item->EntityMapper.mapToDto(item,ImageDTO.class)).toList();
    }

    @Override
    public void deleteImage(List<ImageDTO> imagesToDelete, String id) {
        List<Images> postsList = imagesToDelete.stream().map(item -> EntityMapper.mapToEntity(item,Images.class)).toList();
            imageRepository.deleteAll(postsList);
    }
}
