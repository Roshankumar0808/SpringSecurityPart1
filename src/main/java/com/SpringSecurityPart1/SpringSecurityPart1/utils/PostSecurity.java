package com.SpringSecurityPart1.SpringSecurityPart1.utils;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.PostDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.PostEntity;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.User;
import com.SpringSecurityPart1.SpringSecurityPart1.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

  public  boolean isOwnerOfPost(Long PostId){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostDTO post=postService.getPostById(PostId);
        return post.getAuthor().getId().equals(user.getId());
    }

}
