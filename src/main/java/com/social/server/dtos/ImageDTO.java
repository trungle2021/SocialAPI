package com.social.server.dtos;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Image Order cannot be null")
    private Integer imageOrder;
    private String content;
    private String imageUrl;
    private String owner;
    private String parentId;
    private Integer likeCount;
    private Instant postedAt;
    private Instant updatedAt;
    private Boolean isDeleted;
}
