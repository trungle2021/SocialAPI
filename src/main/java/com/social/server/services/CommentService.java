package com.social.server.services;

import com.social.server.dtos.CommentPostDTO;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<CommentPostDTO> getCommentByPostId(String postId,int offset, int limit);
    CommentPostDTO createComment(CommentPostDTO comment);
    CommentPostDTO updateComment(CommentPostDTO comment);
    void deleteComment(String commentId);
}
