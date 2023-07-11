package com.social.server.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Timestamp;

public class LikePostDTO {
    private String id;
    private String userId;
    private String postId;
    private Timestamp likedAt;
}
