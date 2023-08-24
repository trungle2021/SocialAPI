package com.social.server.controllers;

import com.social.server.dtos.*;
import com.social.server.services.Post.Comment.CommentService;
import com.social.server.services.Post.PostBase.PostBaseService;
import com.social.server.services.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostBaseService postBaseService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/newsfeed/{userId}/{offset}/{limit}")
    public ResponseEntity<Page<PostResponseDTO>> getNewsFeed(@PathVariable String userId, @PathVariable int offset,@PathVariable int limit){
        return ResponseEntity.ok(postService.getNewsFeed(userId,offset,limit));
    }

    @GetMapping("/pagination/{userId}/{offset}/{limit}/{sortBy}")
    public ResponseEntity<Page<PostResponseDTO>> getPostsByUserIdWithPagination(@PathVariable String userId, @PathVariable int offset, @PathVariable int limit, @PathVariable(required = false) String sortBy){
        return ResponseEntity.ok(postService.getPostsByUserIdWithPagination(userId,offset,limit, sortBy));
    }

    @GetMapping("/{postId}/comment/{offset}/{limit}")
    public ResponseEntity<PostResponseDTO> getPostByPostId(@PathVariable String postId,@PathVariable int offset,@PathVariable int limit){
        return ResponseEntity.ok(postService.getPostByPostId(postId,offset,limit));
    }

    @GetMapping("/comments/{parentId}/{offset}/{limit}/child")
    public ResponseEntity<Page<CommentDTO>> getChildCommentByParentId(@PathVariable String parentId, @PathVariable int offset, @PathVariable int limit){
        return ResponseEntity.ok(commentService.getChildCommentByParentId(parentId,offset,limit));
    }
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestCreateDTO postRequestCreateDTO){
        return ResponseEntity.ok(postService.createPost(postRequestCreateDTO));
    }

    @PostMapping("/share")
    public ResponseEntity<PostDTO> sharePost(@RequestBody PostDTO sharePost){
        return ResponseEntity.ok(postService.sharePost(sharePost));
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
