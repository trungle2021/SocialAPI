package com.social.server.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Type cannot be null")
    private String type;
    @NotEmpty(message = "Owner cannot be null")
    private String owner;
    @NotEmpty(message = "Like Count cannot be null")
    private Integer likeCount;
    @NotEmpty(message = "Privacy Status cannot be null")
    private String privacyStatus;
    private String parentId;
    @NotEmpty(message = "Delete Status cannot be null")
    private Boolean isDeleted;
    @NotEmpty(message = "Posted Time cannot be null")
    private Instant postedAt;
    private Instant updatedAt;
}
