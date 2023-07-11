package com.social.server.controllers;

import com.social.server.dtos.PostDTO;
import com.social.server.dtos.PostRequestCreateDTO;
import com.social.server.dtos.PostRequestUpdateDTO;
import com.social.server.dtos.PostResponseDTO;
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
    private final PostService postService;

    @GetMapping(value = "/{userId}/{sortBy}")
    public ResponseEntity<Page<PostDTO>> getPosts(@PathVariable String userId, @PathVariable String sortBy){
        return ResponseEntity.ok(postService.getPostsByUserIdWithSorting(userId,sortBy));
    }
    @GetMapping("/pagination/{userId}/{offset}/{limit}/{sortBy}")
    public ResponseEntity<Page<PostDTO>> getPostsWithPagination(@PathVariable String userId, @PathVariable int offset, @PathVariable int limit, @PathVariable(required = false) String sortBy){
        return ResponseEntity.ok(postService.getPostsByUserIdWithPagination(userId,offset,limit, sortBy));
    }
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestCreateDTO postRequestCreateDTO){
        return ResponseEntity.ok(postService.createPost(postRequestCreateDTO));
    }
    @PutMapping
    public ResponseEntity<PostResponseDTO> updatePost(@RequestBody PostRequestUpdateDTO postRequestUpdateDTO){
        return ResponseEntity.ok(postService.updatePost(postRequestUpdateDTO));
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId){
        postService.deletePost(postId);
       return ResponseEntity.ok("Deleted successfully");
    }

}
