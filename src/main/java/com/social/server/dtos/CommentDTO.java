package com.social.server.dtos;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
    private Boolean isDeleted;
    private Instant postedAt;
    private Instant updatedAt;
    private Long childCommentCount;
}
