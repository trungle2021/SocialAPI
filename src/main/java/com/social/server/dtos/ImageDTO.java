package com.social.server.dtos;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private String id;
    private String postId;
    private Integer orderImage;
    private String content;
    private String imageUrl;
    private String owner;
    private String parentId;
    private Integer likeCount;
    private String privacyStatus;
    private Instant postedAt;
    private Instant updatedAt;
    private Boolean isDeleted;
    private List<LikeDTO> likesList;

}
