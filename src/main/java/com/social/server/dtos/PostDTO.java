package com.social.server.dtos;

import com.social.server.entities.Posts;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class PostDTO {
   private Posts posts;
//   private List<>
}
