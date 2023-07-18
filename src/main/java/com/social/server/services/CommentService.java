package com.social.server.services;

import com.social.server.dtos.CommentDTO;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentDTO> getCommentByParentId(String parentId, int offset, int limit);
    CommentDTO createComment(String postId,CommentDTO comment);
    CommentDTO updateComment(CommentDTO comment);

}
