package com.social.server.controllers;

import com.social.server.entities.Posts;
import com.social.server.services.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/{userId}/{field}")
    public ResponseEntity<List<Posts>> getPosts(@PathVariable String userId, @PathVariable String field){
        return ResponseEntity.ok(postService.getPostsByUserIdWithSorting(userId,field));
    }

    @GetMapping("/pagination/{userId}/{offset}/{limit}/{field}")
    public ResponseEntity<Page<Posts>> getPostsWithPagination(@PathVariable String userId, @PathVariable int offset, @PathVariable int limit,@PathVariable(required = false) String field){
        return ResponseEntity.ok(postService.getPostsByUserIdWithPagination(userId,offset,limit, field));
    }

}
