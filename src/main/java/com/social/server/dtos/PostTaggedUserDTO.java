package com.social.server.dtos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.sql.Timestamp;

public class PostTaggedUserDTO {
    private String taggedUserId;
    private String postId;
    private Timestamp taggedAt;
    private Timestamp updateAt;
}
