package com.social.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String postId;
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
