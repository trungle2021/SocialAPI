package com.social.server.dtos;

import com.social.server.entities.Post.Likes;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;
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
    private String owner;
    private String parentId;
    private Integer likeCount;
    private String privacyId;
    private Boolean isDeleted;
    private Instant postedAt;
    private Instant updatedAt;
    private List<LikeDTO> likesList;

}
