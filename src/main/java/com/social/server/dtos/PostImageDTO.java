package com.social.server.dtos;

import lombok.*;

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
    private Integer orderImage;
}
