package com.social.server.controllers;

import com.social.server.dtos.*;
import com.social.server.services.CommentService;
import com.social.server.services.PostBaseService;
import com.social.server.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostBaseService postBaseService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping(value = "/{userId}/{sortBy}")
    public ResponseEntity<Page<PostDTO>> getPosts(@PathVariable String userId, @PathVariable String sortBy){
        return ResponseEntity.ok(postService.getPostsByUserIdWithSorting(userId,sortBy));
    }
    @GetMapping("/pagination/{userId}/{offset}/{limit}/{sortBy}")
    public ResponseEntity<Page<PostResponseDTO>> getPostsWithPagination(@PathVariable String userId, @PathVariable int offset, @PathVariable int limit, @PathVariable(required = false) String sortBy){
        return ResponseEntity.ok(postService.getPostsByUserIdWithPagination(userId,offset,limit, sortBy));
    }
    @GetMapping("/{postId}/comments/{offset}/{limit}")
    public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable String postId, @PathVariable int offset, @PathVariable int limit){
        return ResponseEntity.ok(commentService.getCommentByParentId(postId,offset,limit));
    }
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestCreateDTO postRequestCreateDTO){
        return ResponseEntity.ok(postService.createPost(postRequestCreateDTO));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable String postId, @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.createComment(postId,commentDTO));
    }


    @PutMapping
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody PostRequestUpdateDTO postRequestUpdateDTO){
        return ResponseEntity.ok(postService.updatePost(postRequestUpdateDTO));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId){
        postBaseService.deleteChild(postId);
       return ResponseEntity.ok("Deleted successfully");
    }

}
