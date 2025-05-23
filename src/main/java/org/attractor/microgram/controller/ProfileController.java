package org.attractor.microgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.dto.UserDto;
import org.attractor.microgram.service.ImageService;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.SubscriptionService;
import org.attractor.microgram.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/user/{username}")
    public String viewUserProfile(@AuthenticationPrincipal User principal, @PathVariable String username, Model model) {
        log.info("Viewing profile for user: {}", username);

        UserDto user = userService.getUserByName(username);


        List<PostDto> posts = postService.getPostsByUserId(user.getId());

        boolean isSubscribed = principal != null && subscriptionService.isSubscribed(principal.getUsername(), username);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("isAuthenticated", principal != null);
        model.addAttribute("isSubscribed", isSubscribed);

        return "profile";
    }

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User principal) {
        return "redirect:/user/" + principal.getUsername();
    }

    @PostMapping("/user/{username}/avatar")
    public String updateAvatar(@AuthenticationPrincipal User principal,
                               @PathVariable String username,
                               @RequestParam("file") MultipartFile file,
                               Model model) {

        if (!file.isEmpty()) {
            String fileName = principal.getUsername() + "_avatar_" + file.getOriginalFilename();

            imageService.uploadImage(principal, file);
        }
        return "redirect:/profile";
    }

    @GetMapping("/user/edit")
    public String showEditProfile(@AuthenticationPrincipal User principal, Model model) {
        UserDto user = userService.getUserByName(principal.getUsername());
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/user/edit")
    public String updateProfile(@AuthenticationPrincipal User principal,
                                @RequestParam(value = "bio", required = false) String bio,
                                Model model) {
        if (principal != null) {
            UserDto user = userService.getUserByName(principal.getUsername());
            if (bio != null) {
                user.setBio(bio);
                userService.updateUser(user);
            }
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "You can only edit your own profile.");
            return "edit-profile";
        }
    }

    @PostMapping("/user/{username}/subscribe")
    public String toggleSubscription(@PathVariable String username, @AuthenticationPrincipal User principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        log.info("Toggling subscription for user: {} by user: {}", username, principal.getUsername());
        if (subscriptionService.isSubscribed(principal.getUsername(), username)) {
            subscriptionService.unsubscribe(principal.getUsername(), username);
        } else {
            subscriptionService.subscribe(principal.getUsername(), username);
        }
        return "redirect:/user/" + username;
    }
}
