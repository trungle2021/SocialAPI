package com.social.server.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.time.Instant;

public class CommentPostDTO {
    private String id;
    private String userId;
    private String postId;
    private String content;
    private Instant postedAt;
    private Instant updateAt;
    private Boolean isDeleted;
}
