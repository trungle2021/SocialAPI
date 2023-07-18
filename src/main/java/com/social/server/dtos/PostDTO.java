package com.social.server.dtos;

import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDTO {
    private String id;
    private String content;
    private String type;
    private String owner;
    private Integer likeCount;
    private String privacyId;
    private String parentId;
    private Boolean isDeleted;
    private Instant postedAt;
    private Instant updatedAt;
    private List<LikeDTO> likesList;
}
