package com.social.server.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDTO {
    private int order;
    private MultipartFile file;
}
