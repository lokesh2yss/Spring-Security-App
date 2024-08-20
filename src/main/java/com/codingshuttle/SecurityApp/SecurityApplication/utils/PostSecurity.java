package com.codingshuttle.SecurityApp.SecurityApplication.utils;

import com.codingshuttle.SecurityApp.SecurityApplication.dto.PostDTO;
import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingshuttle.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO = postService.getPostById(postId);
        return Objects.equals(postDTO.getAuthor().getId(), user.getId());
    }
}
