package org.attractor.microgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.service.ImageService;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;

    @GetMapping("/create")
    public String showCreatePost(@AuthenticationPrincipal User principal, Model model) {
        log.info("Showing create post form for user: {}", principal.getUsername());
        UserDto user = userService.getUserByName(principal.getUsername());
        model.addAttribute("user", user);
        return "posts/create-post";
    }

    @PostMapping("/create")
    public String createPost(@AuthenticationPrincipal User principal,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("description") String description,
                            Model model) {
        log.info("Creating post for user: {}", principal.getUsername());
        if (!image.isEmpty()) {
            String imageUrl = imageService.savePostImage(image, userService.getUserByName(principal.getUsername()).getId());
            PostDto post = PostDto.builder()
                    .imageUrl(imageUrl)
                    .description(description)
                    .likesCount(0L)
                    .commentsCount(0L)
                    .build();
            postService.createPost(principal.getUsername(), post);
            log.info("Post created with imageUrl: {}", imageUrl);
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Image is required.");
            return "posts/create-post";
        }
    }
}