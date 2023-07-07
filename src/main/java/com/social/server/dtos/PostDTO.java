package com.social.server.dtos;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDTO {
    String id;
    private String content;
    private String postOwner;
    private String privacyStatus;
    private Timestamp postedAt;
    private Timestamp updateAt;
    private Boolean isDeleted;
}
