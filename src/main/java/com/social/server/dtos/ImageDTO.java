package com.social.server.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private String id;
    private String content;
    private String imageUrl;
    private String parentType;
    private Boolean isDeleted;
    private Integer orderImage;
}
