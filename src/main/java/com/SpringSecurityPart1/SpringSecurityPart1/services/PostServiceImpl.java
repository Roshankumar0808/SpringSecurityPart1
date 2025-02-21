package com.SpringSecurityPart1.SpringSecurityPart1.services;

import com.SpringSecurityPart1.SpringSecurityPart1.dto.PostDTO;
import com.SpringSecurityPart1.SpringSecurityPart1.entities.PostEntity;
import com.SpringSecurityPart1.SpringSecurityPart1.exceptions.ResourceNotFoundException;
import com.SpringSecurityPart1.SpringSecurityPart1.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepo;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PostDTO> getAllPost() {
        return postRepo.findAll().stream().map(post->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity=modelMapper.map(inputPost,PostEntity.class);
        return modelMapper.map(postRepo.save(postEntity),PostDTO.class);

    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity=postRepo
                .findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post Not found with id"+postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

//    @Override
//    public PostDTO updateDetails(PostDTO input, Long postId) {
//        PostEntity olderpost=postRepo.findById(postId).orElseThrow(()->new RuntimeException("Post Not Found with id "+postId));
//        input.setId(postId);
//        modelMapper.map(input,olderpost);
//        PostEntity newpost=postRepo.save(olderpost);
//        return modelMapper.map(newpost, PostDTO.class);
//
//    }

}
