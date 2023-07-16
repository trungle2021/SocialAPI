package com.social.server.dtos;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class LikeDTO {
    private String id;
    private String userId;
    private String username;
    private Instant likedAt;
}
