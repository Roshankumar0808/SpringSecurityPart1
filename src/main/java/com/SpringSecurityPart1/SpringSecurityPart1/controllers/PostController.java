package com.SpringSecurityPart1.SpringSecurityPart1.controllers;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.PostDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<PostDTO> getAllpost(){
        return postService.getAllPost();
    }

    @GetMapping("/{postId}")
   @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getpostbyId(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO postDetails(@RequestBody PostDTO input){

        return postService.createNewPost(input);
    }

//    @PutMapping("/{postId}")
//    public PostDTO updateDetails(@RequestBody PostDTO input,@PathVariable Long postId){
//
//        return postService.updateDetails(input,postId);
//    }
}
