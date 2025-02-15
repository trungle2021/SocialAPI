package com.social.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTaggedUserDTO {
    private String taggedUserId;
    private String postId;
    private Instant taggedAt;
    private Instant updateAt;
}
