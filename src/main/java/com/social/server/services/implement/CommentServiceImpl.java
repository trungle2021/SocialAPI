package com.social.server.services.implement;

import com.social.server.dtos.CommentDTO;
import com.social.server.entities.Post.Comments;
import com.social.server.entities.Post.Posts;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.repositories.CommentRepository;
import com.social.server.repositories.Post.PostRepository;
import com.social.server.services.CommentService;
import com.social.server.services.LikeService;
import com.social.server.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeService likeService;

    @Override
    public Page<CommentDTO> getCommentByParentId(String parentId, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        List<CommentDTO> comments = commentRepository.findAllByParentId(parentId,pageable)
                .stream()
                .map(item->EntityMapper.mapToDto(item,CommentDTO.class))
                .peek(item -> item.setLikeCount(likeService.getLikeCountOfParentByParentId(item.getId())))
                .toList();
        return new PageImpl<>(comments);
    }

    @Override
    public CommentDTO createComment(String postId, CommentDTO commentDTO) {
        Posts posts = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found","id",postId));
        commentDTO.setPostId(posts.getId());
        Comments comments = EntityMapper.mapToEntity(commentDTO,Comments.class);
        return EntityMapper.mapToDto(commentRepository.save(comments),CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comments comments = EntityMapper.mapToEntity(commentDTO,Comments.class);
        return EntityMapper.mapToDto(commentRepository.save(comments),CommentDTO.class);
    }

}
