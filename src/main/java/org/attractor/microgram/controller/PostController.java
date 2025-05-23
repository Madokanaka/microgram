package org.attractor.microgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.CommentDto;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.service.CommentService;
import org.attractor.microgram.service.ImageService;
import org.attractor.microgram.service.LikeService;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping("/post/create")
    public String showCreatePost(@AuthenticationPrincipal User principal, Model model) {
        log.info("Showing create post form for user: {}", principal.getUsername());
        UserDto user = userService.getUserByName(principal.getUsername());
        model.addAttribute("user", user);
        return "posts/create-post";
    }

    @PostMapping("/post/create")
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

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model, @AuthenticationPrincipal User principal) {
        log.info("Viewing post with id: {}", id);
        PostDto post = postService.getPostById(id);
        UserDto user = userService.getUserByName(post.getUsername());
        boolean hasLiked = principal != null && likeService.hasLiked(id, principal.getUsername());
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("isAuthenticated", principal != null);
        model.addAttribute("comments", commentService.getCommentsByPostId(id));
        model.addAttribute("hasLiked", hasLiked);
        return "posts/post";
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal User principal, Model model) {
        log.info("Attempting to delete post with id: {} by user: {}", id, principal.getUsername());
        PostDto post = postService.getPostById(id);

        if (!post.getUsername().equals(principal.getUsername())) {
            log.warn("User {} attempted to delete post {} that they do not own", principal.getUsername(), id);

            return "redirect:/post" + id;
        }
        commentService.deleteCommentsByPostId(id);
        likeService.deleteLikesByPostId(id);
        postService.deletePost(id);
        log.info("Post with id: {} deleted successfully by user: {}", id, principal.getUsername());
        return "redirect:/profile";
    }

    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam("comment") String content,
                             @AuthenticationPrincipal User principal, Model model) {
        log.info("Adding comment to post with id: {} by user: {}", id, principal.getUsername());
        commentService.createComment(id, principal.getUsername(), content);
        return "redirect:/post/" + id;
    }

    @PostMapping("/post/comment/{id}/delete")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal User principal, Model model) {
        log.info("Attempting to delete comment with id: {} by user: {}", id, principal.getUsername());
        CommentDto comment = commentService.getCommentsByPostId(id).stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        PostDto post = postService.getPostById(comment.getPostId());

        if (!post.getUsername().equals(principal.getUsername())) {
            log.warn("User {} attempted to delete comment {} under post {} that they do not own", principal.getUsername(), id, comment.getPostId());
            model.addAttribute("error", "You can only delete comments under your own posts.");
            UserDto user = userService.getUserByName(post.getUsername());
            model.addAttribute("post", post);
            model.addAttribute("user", user);
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("comments", commentService.getCommentsByPostId(comment.getPostId()));
            return "posts/post";
        }

        commentService.deleteComment(id);
        log.info("Comment with id: {} deleted successfully by user: {}", id, principal.getUsername());
        return "redirect:/post/" + comment.getPostId();
    }

    @PostMapping("/post/{id}/like")
    public String toggleLike(@PathVariable Long id, @AuthenticationPrincipal User principal) {
        log.info("Toggling like for post with id: {} by user: {}", id, principal.getUsername());
        likeService.toggleLike(id, principal.getUsername());
        return "redirect:/post/" + id;
    }

    @GetMapping("/home")
    public String showFeed(@AuthenticationPrincipal User principal, Model model) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        log.info("Showing feed for user: {}", principal.getUsername());
        List<PostDto> feedPosts = postService.getFeedPosts(principal.getUsername());
        model.addAttribute("posts", feedPosts);
        return "posts/feed";
    }
}