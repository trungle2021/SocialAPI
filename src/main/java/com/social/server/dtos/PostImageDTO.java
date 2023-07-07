package com.social.server.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDTO {
    private String id;
    private String imageUrl;
    private String postId;
    private Boolean isDeleted;
    private int order;
    private MultipartFile file;
}
