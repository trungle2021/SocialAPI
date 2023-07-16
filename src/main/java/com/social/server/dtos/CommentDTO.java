package com.social.server.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommentDTO {
    private String id;
    private String content;
    private String imageUrl;
    private String owner;
    private String parentId;
    private Integer likeCount;
    private Boolean isDeleted;
    private Instant postedAt;
    private Instant updatedAt;
    private List<LikeDTO> likesList;
    private List<CommentDTO> replies;
}
