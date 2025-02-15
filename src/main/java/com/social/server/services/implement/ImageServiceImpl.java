package com.social.server.services.implement;

import com.social.server.dtos.ImageDTO;
import com.social.server.entities.Post.Images;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.Post.ImageRepository;
import com.social.server.services.ImageService;
import com.social.server.services.LikeService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final LikeService likeService;
    @Override
    public List<ImageDTO> getImagesByPostId(String postId) {
               return imageRepository.findAllByParentId(postId, Sort.by(Sort.Direction.ASC,"imageOrder")).stream()
                       .map(item -> EntityMapper.mapToDto(item,ImageDTO.class))
                       .peek(item -> item.setLikeCount(likeService.getLikeCountOfParentByParentId(item.getId())))
                       .toList();
    }

    @Override
    public ImageDTO getImage(String postId, String imageId) {
        Images images = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Image not found", "id", imageId));
        return EntityMapper.mapToDto(images,ImageDTO.class);
    }

    @Override
    public List<ImageDTO> createImage(List<ImageDTO> data, String postId) {

        List<Images> images = data.stream()
                .map(item -> EntityMapper.mapToEntity(item,Images.class))
                .peek(item->item.setParentId(postId))
                .toList();

        List<Images> listImagesAfterCreate = imageRepository.saveAll(images);

        return listImagesAfterCreate.stream().map(item -> EntityMapper.mapToDto(item,ImageDTO.class)).toList();
    }

    @Override
    public List<ImageDTO> updateImage(List<ImageDTO> imagesToUpdate, String postId) {
        List<Images> postsList = imagesToUpdate.stream().map(item -> EntityMapper.mapToEntity(item,Images.class)).toList();
        return imageRepository.saveAll(postsList).stream().map(item->EntityMapper.mapToDto(item,ImageDTO.class)).toList();
    }


}
