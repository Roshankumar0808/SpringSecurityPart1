package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

   // PostDTO updateDetails(PostDTO input, Long postId);
}
